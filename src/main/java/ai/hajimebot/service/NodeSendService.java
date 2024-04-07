package ai.hajimebot.service;

import com.mybatisflex.core.service.IService;
import ai.hajimebot.entity.NodeSendVo;
import ai.hajimebot.global.R;

/**
 *  Service Interface
 *
 * @author xc.deng
 * @since 1.0.1
 */
public interface NodeSendService extends IService<NodeSendVo> {
    public R<?> nodeSend(NodeSendVo nodeSendVo);
    public R<?> sendDownLoad(String id);
    public R<?> deleteFile(String id);
}
