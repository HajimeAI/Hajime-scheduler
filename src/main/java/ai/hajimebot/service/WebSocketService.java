package ai.hajimebot.service;


import ai.hajimebot.entity.IndexedVo;
import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.table.IndexedVoTableDef;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.global.LogEvent;
import ai.hajimebot.global.LogEventPublisher;
import ai.hajimebot.global.LogEventUtils;
import ai.hajimebot.mapper.IndexedMapper;
import ai.hajimebot.mapper.NodeMapper;
import ai.hajimebot.pojo.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @ClassName WebSocket
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
@ServerEndpoint("/ws/instance")
public class WebSocketService {

    //client session
    private Session session;
    /**
     * User ID
     */
    private String nodeId;

    private String imei;

    private String ip;

    public List<String> ipList = new ArrayList<>();

    /**
     * It is used to store the MyWebSocket object corresponding to each client.。
     */
    private static CopyOnWriteArraySet<WebSocketService> webSockets = new CopyOnWriteArraySet<>();
    /**
     * Used to store user information for wire connections
     */
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();


    private static NodeMapper nodeMapper;


    private static IndexedMapper indexedMapper;

    private static LogEventPublisher logEventPublisher;

    @Autowired
    public void setNodeMapper(NodeMapper nodeMapper) {
        WebSocketService.nodeMapper = nodeMapper;
    }

    @Autowired
    public void setIndexedMapper(IndexedMapper indexedMapper) {
        WebSocketService.indexedMapper = indexedMapper;
    }

    @Autowired
    public void setLogEventPublisher(LogEventPublisher logEventPublisher) {
        WebSocketService.logEventPublisher = logEventPublisher;
    }

    /**
     * onOpen
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("nodeMapper", WebSocketService.nodeMapper);
        webSockets.add(this);
        try {
            this.session = session;
            Map<String, List<String>> parameterMap = session.getRequestParameterMap();
            if (parameterMap.containsKey("imei")) {
                this.imei = parameterMap.get("imei").get(0);
                log.info("device imei:" + this.imei);
            }
            if (parameterMap.containsKey("ip")) {
                this.ip = parameterMap.get("ip").get(0);
                log.info("device ip:" + this.ip);
            }
            NodeVo nodeVo = null;
            if (this.imei != null) {
                nodeVo = nodeMapper.selectOneByQuery(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(this.imei)));
                if (nodeVo != null) {
                    //It has already been registered, and the update node is in a healthy state
                    this.ip = nodeVo.getIp();
                    nodeVo.setHealthy(1L);
                    nodeVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    nodeMapper.update(nodeVo);
                } else {
                    nodeVo = createNode();
                }
            } else {
                nodeVo = createNode();
            }
            if (BeanUtil.isEmpty(nodeVo) || nodeVo.getId() == null || nodeVo.getId() <= 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("msg", "login failed");
                session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MessageBean.<Map<String, Object>>builder()
                        .msgType("login")
                        .data(map)
                        .build()));
                session.close();
            } else {
                this.nodeId = String.valueOf(nodeVo.getId());
                sessionPool.put(String.valueOf(nodeVo.getId()), session);
                // respond_to_device_logins
                //edge -c hajime -k hajimegogogo! -a 10.10.10.12 -f -l n2n.dorylus.chat:6777
                String msg = JSONUtil.toJsonStr(MessageBean.<NodeLoginResp>builder()
                        .msgType("login")
                        .data(NodeLoginResp.builder()
                                .cmd("edge -c hajime -k hajimegogogo! -a " + this.ip + " -f -l n2n.dorylus.chat:6777")
                                .id(nodeVo.getId())
                                .healthy(nodeVo.getHealthy())
                                .imei(nodeVo.getImei())
                                .type(nodeVo.getType())
                                .ip(this.ip)
                                .build())
                        .build());
                session.getAsyncRemote().sendText(msg);
                // Send a list of indexes
                List<IndexedVo> list = indexedMapper.selectListByQuery(QueryWrapper.create()
                        .select(IndexedVoTableDef.INDEXED_VO.ID, IndexedVoTableDef.INDEXED_VO.NAME));
                session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MessageBean.<List<IndexedVo>>builder()
                        .msgType("query_indexed")
                        .data(list)
                        .build()));
                // Log in logs
                logEventPublisher.publishEvent(LogEvent.builder()
                        .type("login")
                        .content(nodeVo.getImei() + " The device is connected and online...")
                        .name(nodeVo.getImei())
                        .build());
                SpringUtil.getApplicationContext().publishEvent(EventBean.builder()
                        .kind(LogEventUtils.DEVICE_ONLINE)
                        .nodeId(nodeVo.getImei())
                        .build());
                log.info("[websocket message] has a new connection, the User ID is [{}] and the total is [{}]", nodeId, webSockets.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NodeVo createNode() {
        String listSql = "select ip from ip_config";
        List<Row> rows = Db.selectListBySql(listSql);
        ipList = rows.stream().map(o->StrUtil.toString(o.get("ip"))).collect(Collectors.toList());
        if (this.imei == null) {
            this.imei = IdUtil.simpleUUID();
        }
        //新节点
        List<NodeVo> list = nodeMapper.selectAll();
        String deviceName = getDeviceName(list.size());
        Set<String> set = new HashSet<>();
        for (NodeVo vo : list) {
            set.add(vo.getIp());
        }
        for (String ip : ipList) {
            if (set.contains(ip)) {
                continue;
            }
            this.ip = ip;
        }

        if (StrUtil.isEmpty(this.ip)) {
            return null;
        } else {
            IndexedVo indexedVo = loadBalancer();
            NodeVo vo = NodeVo.builder()
                    .name(deviceName)
                    .imei(this.imei)
                    .ip(this.ip)
                    .type(StrUtil.toString(indexedVo.getId()))
                    .deviceType("0")
                    .healthy(1L)
                    .createTime(new Timestamp(System.currentTimeMillis()))
                    .updateTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            nodeMapper.insert(vo);
            //The index library settings have been bound
//            indexedVo.setEnable("0");
//            indexedMapper.update(indexedVo);
            return vo;
        }
    }


    private String getDeviceName(int num) {
        return "hjm" + StrUtil.padPre(StrUtil.toString(num + 1), 5, "0");
    }

    private static int index = 0;

    private IndexedVo loadBalancer() {
        List<IndexedVo> list = indexedMapper.selectAll();
        IndexedVo vo = list.get(index);
        index = (index + 1) % list.size();
        log.info("Polling algorithm:{} ", vo.getId());
        return vo;
    }

    /**
     * onClose
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            if (this.nodeId != null) {
                sessionPool.remove(this.nodeId);
                NodeVo nodeVo = nodeMapper.selectOneByQuery(QueryWrapper.create()
                        .where(NodeVoTableDef.NODE_VO.ID.eq(this.nodeId)));
                if (nodeVo != null) {
                    nodeVo.setHealthy(0L);
                    nodeMapper.update(nodeVo);
                    logEventPublisher.publishEvent(LogEvent.builder()
                            .content(nodeVo.getImei() + " close websocket connection.")
                            .type("close")
                            .name(nodeVo.getImei())
                            .build());
                    SpringUtil.getApplicationContext().publishEvent(EventBean.builder()
                            .kind(LogEventUtils.DEVICE_OFFLINE)
                            .nodeId(nodeVo.getImei())
                            .build());
                }
//                sendAllMessage();
            }
            log.info("[webSocket message] connection is disconnected, the total is: {}", webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * onMessage
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        MessageBean nodeBean = null;
        try {
            log.info("[websocket message] received client message:" + JSONUtil.toJsonStr(message));
            nodeBean = JSONUtil.toBean(message, MessageBean.class);
            switch (nodeBean.getMsgType()) {
                case "ping":
                    //ping
                    break;
                case "pong":
                    // Heartbeat response
                    NodeBean vo = BeanUtil.toBean(nodeBean.getData(), NodeBean.class);
                    NodeVo nodeVo = nodeMapper.selectOneByQuery(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(this.imei)));
                    if (nodeVo != null && nodeVo.getHealthy() == 0) {
                        //Modify the health status of a node
                        nodeVo.setHealthy(1L);
                        nodeVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        nodeMapper.update(nodeVo);
//                        logEventPublisher.publishEvent(LogEvent.builder()
//                                .type("pong")
//                                .content(message)
//                                .name(nodeVo.getImei())
//                                .build());
                    }
                    break;
                case "query":
                    NodeBean bean = BeanUtil.toBean(nodeBean.getData(), NodeBean.class);
                    List<NodeVo> nodeVos = nodeMapper.selectListByQuery(QueryWrapper.create().select(NodeVoTableDef.NODE_VO.IMEI, NodeVoTableDef.NODE_VO.IP, NodeVoTableDef.NODE_VO.TYPE)
                            .where(NodeVoTableDef.NODE_VO.HEALTHY.eq(1)
                                    .and(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("0"))
                                    .and(NodeVoTableDef.NODE_VO.TYPE.eq(bean.getType()))));
                    session.getAsyncRemote().sendText(JSONUtil.toJsonStr(
                            MessageBean.<List<NodeVo>>builder()
                                    .msgType("query")
                                    .data(nodeVos)
                                    .build()));
                    logEventPublisher.publishEvent(LogEvent.builder()
                            .type("query")
                            .content(bean.getImei() + " query device list info.")
                            .name(bean.getImei())
                            .build());
                    break;
                case "log":
                    NodeBean log = BeanUtil.toBean(nodeBean.getData(), NodeBean.class);
                    logEventPublisher.publishEvent(LogEvent.builder()
                            .type("log")
                            .content(log.getImei() + " " + log.getContent())
                            .name(log.getImei())
                            .build());
                    break;
                case "node_detail":
                    NodeDetail nodeDetail = BeanUtil.toBean(nodeBean.getData(), NodeDetail.class);
                    NodeVo query = nodeMapper.selectOneByQuery(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(nodeDetail.getImei())));
                    query.setDetail(JSONUtil.toJsonStr(nodeDetail));
                    nodeMapper.update(query);
                    break;
                case "event":
                    EventBean eventBean = BeanUtil.toBean(nodeBean.getData(), EventBean.class);
                    SpringUtil.getApplicationContext().publishEvent(eventBean);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            String s = "{\"msgType\":\"ping\",\"data\":{\"imei\":5}}";
            session.getAsyncRemote().sendText(JSONUtil.toJsonStr(
                    MessageBean.builder()
                            .cmd("Please send a message body in JSON format, the structure refers to data")
                            .msgType("error")
                            .data(JSONUtil.parseObj(s))
                            .build()
            ));
        }

    }

    /**
     * onError
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error: {}", error.getMessage());
    }


    public void sendAllMessage() {
        log.info("[websocket message]: Broadcast messages");
        List<NodeVo> nodeVos = nodeMapper.selectListByQuery(QueryWrapper.create()
                .select(NodeVoTableDef.NODE_VO.ID)
                .where(NodeVoTableDef.NODE_VO.HEALTHY.eq(1)
                        .and(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("0"))));
        MessageBean<List> messageBean = new MessageBean<>();
        messageBean.setMsgType("query");
        messageBean.setData(nodeVos);
        for (WebSocketService webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(JSONUtil.toJsonStr(messageBean));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcast a list of all user indexes message
     */
    public void sendIndexAllMessage() {
        log.info("[websocket message]All Message ");
        List<IndexedVo> list = indexedMapper.selectListByQuery(QueryWrapper.create()
                .select(IndexedVoTableDef.INDEXED_VO.ID, IndexedVoTableDef.INDEXED_VO.NAME));
        for (WebSocketService webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(JSONUtil.toJsonStr(MessageBean.<List<IndexedVo>>builder()
                            .msgType("query_indexed")
                            .data(list)
                            .build()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // This is a single point of message
    public void sendOneMessage(String nodeId, String message) {
        Session session = sessionPool.get(nodeId);

        if (session != null && session.isOpen()) {
            try {
                log.info("[websocket message] single point message:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // This is a single point of message (multiplayer)
    public void sendMoreMessage(List<NodeVo> list, String path, String fileName) {
        MessageBean<Map<String, Object>> messageBean = new MessageBean<>();
        messageBean.setMsgType("file");
        for (NodeVo vo : list) {
            List<WebSocketService> wsList = webSockets.stream().filter(o -> o.nodeId.equals(String.valueOf(vo.getId()))).collect(Collectors.toList());
            wsList.forEach(item -> {
                if (item.session != null && item.session.isOpen()) {
                    File file = new File(path);
                    if (file.exists()) {
                        try {
                            log.info("[websocket message] file message:" + fileName);
                            Map<String, Object> map = new HashMap<>();
                            map.put("fileName", fileName);
                            messageBean.setData(map);
                            item.session.getAsyncRemote().sendText(JSONUtil.toJsonStr(messageBean));
                            logEventPublisher.publishEvent(LogEvent.builder()
                                    .type("send file")
                                    .content("cloud server sends file " + fileName + " to " + vo.getImei())
                                    .name("server center")
                                    .build());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    public void sendFileDelMessage(List<NodeVo> list, String fileName) {
        MessageBean<Map<String, Object>> messageBean = new MessageBean<>();
        messageBean.setMsgType("file_del");
        for (NodeVo vo : list) {
            List<WebSocketService> wsList = webSockets.stream().filter(o -> o.nodeId.equals(String.valueOf(vo.getId()))).collect(Collectors.toList());
            wsList.forEach(item -> {
                if (item.session != null && item.session.isOpen()) {
                    try {
                        log.info("[websocket message] file message:" + fileName);
                        Map<String, Object> map = new HashMap<>();
                        map.put("fileName", fileName);
                        messageBean.setData(map);
                        item.session.getAsyncRemote().sendText(JSONUtil.toJsonStr(messageBean));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public void sendOnlineMessage(String message) {
        log.info("Heartbeat Timer Execution");
        MessageBean<NodeBean> messageBean = new MessageBean<>();
        messageBean.setMsgType("ping");
        webSockets.forEach(ws -> {
            try {
                NodeVo nodeVo = nodeMapper.selectOneById(ws.nodeId);
                messageBean.setData(NodeBean.builder().imei(String.valueOf(nodeVo.getImei())).build());
                log.info("[websocket message] Device ID:" + ws.nodeId + "IMEI: " + nodeVo.getImei());
                ws.session.getAsyncRemote().sendText(JSONUtil.toJsonStr(messageBean));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * The health status of the node is unavailable on a scheduled basis
     */
    public void handleNodeHealthy() {
        webSockets.forEach(ws -> {
            NodeVo vo = nodeMapper.selectOneById(ws.nodeId);
            if (vo != null && vo.getHealthy() == 0 && ws.session.isOpen()) {
                try {
                    log.info("[websocket message] active disconnect id:" + ws.nodeId + "device IMEI: " + vo.getImei());
                    ws.session.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


}


