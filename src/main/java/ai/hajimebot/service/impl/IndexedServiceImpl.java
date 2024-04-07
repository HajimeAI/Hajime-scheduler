package ai.hajimebot.service.impl;

import ai.hajimebot.entity.IndexedVo;
import ai.hajimebot.mapper.IndexedMapper;
import ai.hajimebot.service.IndexedService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class IndexedServiceImpl extends ServiceImpl<IndexedMapper, IndexedVo> implements IndexedService {

}
