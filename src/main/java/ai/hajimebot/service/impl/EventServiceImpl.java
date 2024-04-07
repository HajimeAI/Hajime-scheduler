package ai.hajimebot.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import ai.hajimebot.entity.EventVo;
import ai.hajimebot.mapper.EventMapper;
import ai.hajimebot.service.EventService;
import org.springframework.stereotype.Service;

/**
 *  Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, EventVo> implements EventService {

}
