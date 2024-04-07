package ai.hajimebot.service.impl;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.global.R;
import ai.hajimebot.mapper.NodeMapper;
import ai.hajimebot.service.NodeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.DbChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer implementation
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, NodeVo> implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public R<Page<NodeVo>> queryNodeList(Page<NodeVo> page, QueryWrapper query) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteAllDataByImei(String imei) {
        NodeVo nodeVo = nodeMapper.selectOneByQuery(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(imei)));
        // delete node table data
        String sql = "delete from node where imei = ?";
        // delete log table data
        String logSql = "delete from log where op = ?";
        // delete my_device table data
        String mySql = "delete from my_device where imei = ?";
        //delete node_send table data
        String sendSql = "delete from node_send where nid = ?";
        Db.deleteBySql(sql, imei);
        Db.deleteBySql(logSql, imei);
        Db.deleteBySql(mySql, imei);
        Db.deleteBySql(sendSql, nodeVo.getId());
        return R.ok("delete all data success");
    }
}
