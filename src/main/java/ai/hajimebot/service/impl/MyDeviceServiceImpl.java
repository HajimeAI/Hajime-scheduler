package ai.hajimebot.service.impl;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.table.MyDeviceVoTableDef;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import ai.hajimebot.mapper.NodeMapper;
import ai.hajimebot.pojo.NodeDetail;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.SqlUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import ai.hajimebot.entity.MyDeviceVo;
import ai.hajimebot.mapper.MyDeviceMapper;
import ai.hajimebot.service.MyDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class MyDeviceServiceImpl extends ServiceImpl<MyDeviceMapper, MyDeviceVo> implements MyDeviceService {

    @Autowired
    private MyDeviceMapper myDeviceMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public R<?> insertData(MyDeviceVo myDeviceVo) {
        List<NodeVo> list = nodeMapper.selectListByQuery(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(myDeviceVo.getImei())));
        if (!list.isEmpty()) {
            myDeviceVo.setUid(StpUtil.getLoginIdAsLong());
            myDeviceVo.setNid(list.get(0).getId());
            myDeviceVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            int num = myDeviceMapper.insert(myDeviceVo);
            if (SqlUtil.toBool(num)) {
                return R.ok("Add Success");
            }
        }
        return R.fail("Add Failed");
    }

    @Override
    public R<?> getList(ReqPage<NodeVo> reqPage) {
        Page<NodeVo> page = new Page<>(reqPage.getPageNumber(), reqPage.getPageSize());
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (reqPage.getData() != null && !StrUtil.isBlank(reqPage.getData().getImei())){
            queryWrapper.from(MyDeviceVoTableDef.MY_DEVICE_VO).join(NodeVoTableDef.NODE_VO)
                    .on(NodeVoTableDef.NODE_VO.IMEI.like(reqPage.getData().getImei()).and(MyDeviceVoTableDef.MY_DEVICE_VO.NID.eq(NodeVoTableDef.NODE_VO.ID)))
                    .where(MyDeviceVoTableDef.MY_DEVICE_VO.UID.eq(StpUtil.getLoginIdAsLong()));

        } else {
            queryWrapper.from(MyDeviceVoTableDef.MY_DEVICE_VO).join(NodeVoTableDef.NODE_VO)
                    .on(MyDeviceVoTableDef.MY_DEVICE_VO.NID.eq(NodeVoTableDef.NODE_VO.ID))
                    .where(MyDeviceVoTableDef.MY_DEVICE_VO.UID.eq(StpUtil.getLoginIdAsLong()));

        }
        return R.ok(nodeMapper.paginateAs(page,queryWrapper,null));
    }
}
