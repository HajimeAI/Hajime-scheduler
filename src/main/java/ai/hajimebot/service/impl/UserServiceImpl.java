package ai.hajimebot.service.impl;

import ai.hajimebot.entity.table.UserVoTableDef;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ai.hajimebot.entity.UserVo;
import ai.hajimebot.mapper.UserMapper;
import ai.hajimebot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserVo> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserVo getUser(String username, String password) {
        QueryWrapper query = QueryWrapper.create()
                .from(UserVoTableDef.USER_VO)
                .where(UserVoTableDef.USER_VO.USERNAME.eq(username))
                .and(UserVoTableDef.USER_VO.PASSWORD.eq(password));
        return userMapper.selectOneByQuery(query);
    }
}
