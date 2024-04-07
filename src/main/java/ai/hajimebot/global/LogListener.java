package ai.hajimebot.global;

import ai.hajimebot.config.HttpConfig;
import ai.hajimebot.entity.EventVo;
import ai.hajimebot.entity.LogVo;
import ai.hajimebot.mapper.EventMapper;
import ai.hajimebot.mapper.LogMapper;
import ai.hajimebot.pojo.EventBean;
import ai.hajimebot.service.WebSocketAdminService;
import ai.hajimebot.service.WebSocketShowEvent;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Slf4j
public class LogListener {


    @Autowired
    private LogMapper logMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private WebSocketAdminService webSocketAdminService;

    @Autowired
    private WebSocketShowEvent webSocketShowEvent;

    @Autowired
    private HttpConfig httpConfig;

    public static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(10);
    }


    @EventListener
    @Async
    public void listenerLog(LogEvent event) {
        String message = DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss") + " " + event.getContent();
        logMapper.insert(LogVo.builder()
                .type(event.getType())
                .op(event.getName())
                .createTime(new Timestamp(System.currentTimeMillis()))
                .content(message)
                .build());
        webSocketAdminService.sendAllMessage(message);
        log.info(String.format("%s listens to log events:%s.", LogListener.class.getName(), event));
    }

    @EventListener
    @Async
    public void listenerEvent(EventBean event) {
        eventMapper.insert(EventVo.builder()
                .kind(event.getKind())
                .nodeId(event.getNodeId())
                .createTime(new Timestamp(System.currentTimeMillis()))
                .content(JSONUtil.toJsonStr(event.getContent()))
                .build());
        webSocketShowEvent.sendAllMessage(JSONUtil.toJsonStr(event));
        log.info(String.format("%s listens to log events:%s.", LogListener.class.getName(), event));

        switch (event.getKind()) {
            case "vector_db_response":
            case "query_vector_db":
            case "tts":
            case "ars":
                ThreadUtil.execute(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("task_id", IdUtil.simpleUUID());
                    map.put("node_id", event.getNodeId());
                    map.put("data", Base64.encode(JSONUtil.toJsonStr(event)));
                    log.info("http api request data:{}", JSONUtil.toJsonStr(map));
                    String result = HttpRequest.post(httpConfig.getUrl() + "hash_to_blockchain")
                            .header("X-Auth", "iYO/yDEaVm6iAg==")
                            .header(Header.CONTENT_TYPE, "application/json")
                            .header(Header.ACCEPT, "application/json")
                            .body(JSONUtil.toJsonStr(map))
                            .timeout(30000)//timeout
                            .execute().body();
                    log.info("http api response: {}", result);
                });
                break;
            default:
                break;
        }
    }
}