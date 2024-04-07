package ai.hajimebot.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ai.hajimebot.entity.LogVo;
import ai.hajimebot.entity.table.LogVoTableDef;
import ai.hajimebot.global.R;
import ai.hajimebot.mapper.LogMapper;
import ai.hajimebot.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogVo> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public R<?> getList(String content) {
        List<LogVo> logVos = logMapper.selectListByQuery(QueryWrapper.create()
                .select(LogVoTableDef.LOG_VO.ID,LogVoTableDef.LOG_VO.CONTENT)
                .where(LogVoTableDef.LOG_VO.CONTENT.like(content)).orderBy(LogVoTableDef.LOG_VO.ID,false).limit(10));
        return R.ok(logVos);
    }
}
