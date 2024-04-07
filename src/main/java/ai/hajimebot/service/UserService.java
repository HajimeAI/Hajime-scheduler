package ai.hajimebot.service;

import com.mybatisflex.core.service.IService;
import ai.hajimebot.entity.UserVo;

/**
 *  Service Interface
 *
 * @author xc.deng
 * @since 1.0.1
 */
public interface UserService extends IService<UserVo> {
    public UserVo getUser(String username, String password);
}
