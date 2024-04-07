package ai.hajimebot.jobs;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.service.WebSocketService;
import com.mybatisflex.core.update.UpdateChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskUtil {

    @Autowired
    private WebSocketService webSocket;

    @Scheduled(cron = "0/3 * * * * ?")
    public void pingTask() {
        webSocket.sendOnlineMessage("Heartbeat detection");
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void closeNode() {
        webSocket.handleNodeHealthy();
        boolean update = UpdateChain.of(NodeVo.class)
                .set(NodeVo::getHealthy, "0")
                .where(NodeVo::getHealthy).eq(1)
                .and(NodeVo::getDeviceType).eq("0")
                .update();
        log.info("update status: {}", update);

    }
}
