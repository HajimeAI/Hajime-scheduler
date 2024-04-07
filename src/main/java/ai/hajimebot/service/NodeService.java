package ai.hajimebot.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.global.R;

/**
 * Service Interface
 *
 * @author xc.deng
 * @since 1.0.1
 */
public interface NodeService extends IService<NodeVo> {
    R<Page<NodeVo>> queryNodeList(Page<NodeVo> page, QueryWrapper query);

    R<?> deleteAllDataByImei(String imei);

}
