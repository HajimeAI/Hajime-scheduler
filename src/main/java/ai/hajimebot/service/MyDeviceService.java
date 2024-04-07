package ai.hajimebot.service;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import com.mybatisflex.core.service.IService;
import ai.hajimebot.entity.MyDeviceVo;

/**
 *  Service Interface
 *
 * @author xc.deng
 * @since 1.0.1
 */
public interface MyDeviceService extends IService<MyDeviceVo> {

 public R<?> insertData(MyDeviceVo myDeviceVo);

 public R<?> getList(ReqPage<NodeVo> reqPage);
}
