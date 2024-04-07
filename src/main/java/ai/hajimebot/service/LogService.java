package ai.hajimebot.service;

import com.mybatisflex.core.service.IService;
import ai.hajimebot.entity.LogVo;
import ai.hajimebot.global.R;

/**
 *  Service Interface
 *
 * @author xc.deng
 * @since 1.0.1
 */
public interface LogService extends IService<LogVo> {

    R<?> getList(String content);
}
