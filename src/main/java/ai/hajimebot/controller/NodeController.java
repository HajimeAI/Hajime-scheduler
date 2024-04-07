package ai.hajimebot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import ai.hajimebot.service.NodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/nodeVo")
@Api(tags = "NodeController")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    /**
     * save
     *
     * @param nodeVo
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public R<?> save(@RequestBody NodeVo nodeVo) {
        boolean status = nodeService.save(nodeVo);
        if(status) {
            return R.ok("Save success");
        }
        return R.fail("Save failed");
    }

    /**
     * Delete based on the primary key
     *
     * @param id
     * @return {@code true} delete succeeded, {@code false} delete failed
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("delete")
    public R<?> remove(@PathVariable Serializable id) {
        boolean status = nodeService.removeById(id);
        if(status) {
            return R.ok("delete success");
        }
        return R.fail("delete failed");
    }

    /**
     * Updates based on the primary key.
     *
     * @param nodeVo
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public R<?> update(@RequestBody NodeVo nodeVo) {
        boolean status = nodeService.updateById(nodeVo);
        if(status) {
            return R.ok("update success");
        }
        return R.fail("update failed");
    }

    /**
     * Query all.
     *
     * @return All data
     */
    @GetMapping("list")
    @ApiOperation("query all")
    public R<List<NodeVo>> list() {
        return R.ok(nodeService.list());
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("get info")
    public NodeVo getInfo(@PathVariable Serializable id) {
        return nodeService.getById(id);
    }

    /**
     * Paginated queries.
     *
     * @param reqPage Pagination objects
     * @return Pagination objects
     */
    @PostMapping("page")
    @ApiOperation("Paginated queries")
    public R<?> page(@RequestBody ReqPage<NodeVo> reqPage) {
        Page<NodeVo> page = new Page<>(reqPage.getPageNumber(), reqPage.getPageSize());
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (reqPage.getData() != null && !StrUtil.isBlank(reqPage.getData().getImei()))
            queryWrapper.where(NodeVoTableDef.NODE_VO.IMEI.like(reqPage.getData().getImei()));
        return R.ok(nodeService.page(page, queryWrapper));
    }

    @DeleteMapping("removeByImei/{imei}")
    @ApiOperation("delete")
    @SaIgnore
    public R<?> removeByImei(@PathVariable String imei) {
        return nodeService.deleteAllDataByImei(imei);
    }
}
