package ai.hajimebot.service.impl;

import ai.hajimebot.config.FileConfig;
import ai.hajimebot.entity.NodeSendVo;
import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.global.LogEvent;
import ai.hajimebot.global.LogEventPublisher;
import ai.hajimebot.global.LogEventUtils;
import ai.hajimebot.global.R;
import ai.hajimebot.mapper.NodeMapper;
import ai.hajimebot.mapper.NodeSendMapper;
import ai.hajimebot.pojo.EventBean;
import ai.hajimebot.service.NodeSendService;
import ai.hajimebot.service.WebSocketService;
import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.SqlUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class NodeSendServiceImpl extends ServiceImpl<NodeSendMapper, NodeSendVo> implements NodeSendService {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private NodeSendMapper nodeSendMapper;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private LogEventPublisher logEventPublisher;

    public R<?> nodeSend(NodeSendVo nodeSendVo) {
        List<NodeVo> list = nodeMapper.selectListByQuery(QueryWrapper.create()
                .select(NodeVoTableDef.NODE_VO.ID, NodeVoTableDef.NODE_VO.IMEI)
                .where(NodeVoTableDef.NODE_VO.TYPE.eq(nodeSendVo.getNid())
                        .and(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("0"))
                ));
        webSocketService.sendMoreMessage(list, fileConfig.getPath() + File.separator + nodeSendVo.getContent(), nodeSendVo.getContent());
        nodeSendVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        nodeSendVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean status = super.save(nodeSendVo);
        if (status) {
            NodeVo centerServer = nodeMapper.selectOneByQuery(QueryWrapper.create()
                    .select(NodeVoTableDef.NODE_VO.ID, NodeVoTableDef.NODE_VO.IMEI)
                    .where(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("1")));
            logEventPublisher.publishEvent(LogEvent.builder()
                    .type("upload file")
                    .content("upload file " + nodeSendVo.getContent())
                    .name("server center")
                    .build());
            Map<String, Object> content = new HashMap<>();
            content.put("text", nodeSendVo.getContent());
            content.put("from_node", centerServer.getImei());
            content.put("to_node", list);
            SpringUtil.getApplicationContext().publishEvent(EventBean.builder()
                    .kind(LogEventUtils.SEND_FILE_TO_NODE)
                    .content(content)
                    .build());
            return R.ok("Save success");
        }
        return R.fail("Save failed");
    }

    @Override
    public R<?> sendDownLoad(String id) {
        NodeSendVo nodeSendVo = nodeSendMapper.selectOneById(id);
        List<NodeVo> list = nodeMapper.selectListByQuery(QueryWrapper.create()
                .select(NodeVoTableDef.NODE_VO.ID, NodeVoTableDef.NODE_VO.IMEI)
                .where(NodeVoTableDef.NODE_VO.TYPE.eq(nodeSendVo.getNid())
                        .and(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("0"))
                ));
        webSocketService.sendMoreMessage(list, fileConfig.getPath() + File.separator + nodeSendVo.getContent(), nodeSendVo.getContent());
        logEventPublisher.publishEvent(LogEvent.builder()
                .content("cloud server distribute file " + nodeSendVo.getContent())
                .type("distribute file")
                .name("server center")
                .build());
        return R.ok("Operation Success");
    }

    @Override
    public R<?> deleteFile(String id) {
        NodeSendVo nodeSendVo = nodeSendMapper.selectOneById(id);
        List<NodeVo> list = nodeMapper.selectListByQuery(QueryWrapper.create()
                .select(NodeVoTableDef.NODE_VO.ID)
                .where(NodeVoTableDef.NODE_VO.TYPE.eq(nodeSendVo.getNid())
                        .and(NodeVoTableDef.NODE_VO.DEVICE_TYPE.eq("0"))
                ));
        webSocketService.sendFileDelMessage(list, nodeSendVo.getContent());
        int status = nodeSendMapper.deleteById(id);
        if (SqlUtil.toBool(status)) {
            logEventPublisher.publishEvent(LogEvent.builder()
                    .type("delete file")
                    .content("delete file " + nodeSendVo.getContent())
                    .name("server center")
                    .build());
            return R.ok("delete success");
        }
        return R.fail("delete failed");
    }
}
