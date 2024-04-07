package ai.hajimebot.controller;

import com.mybatisflex.core.paginate.Page;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import ai.hajimebot.entity.NodeSendVo;
import ai.hajimebot.service.NodeSendService;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/nodeSendVo")
@Api(tags = "NodeSendController")
public class NodeSendController {

    @Autowired
    private NodeSendService nodeSendService;

    /**
     * save
     *
     * @param nodeSendVo
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public R<?> save(@RequestBody NodeSendVo nodeSendVo) {
        return nodeSendService.nodeSend(nodeSendVo);
    }

    @GetMapping("download/{id}")
    @ApiOperation("下发文件")
    public R<?> sendDownLoad(@PathVariable String id) {
        return nodeSendService.sendDownLoad(id);
    }

    /**
     * Delete based on the primary key
     *
     * @param id
     * @return {@code true} delete succeeded, {@code false} delete failed
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("delete")
    public R<?> remove(@PathVariable String id) {
        return nodeSendService.deleteFile(id);
    }

    /**
     * Updates based on the primary key.
     *
     * @param nodeSendVo
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public R<?> update(@RequestBody NodeSendVo nodeSendVo) {
        boolean status = nodeSendService.updateById(nodeSendVo);
        if (status) {
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
    public R<List<NodeSendVo>> list() {
        return R.ok(nodeSendService.list());
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("get info")
    public NodeSendVo getInfo(@PathVariable Serializable id) {
        return nodeSendService.getById(id);
    }

    /**
     * Paginated queries.
     *
     * @param reqPage Pagination objects
     * @return Pagination objects
     */
    @PostMapping("page")
    @ApiOperation("Paginated queries")
    public R<Page<NodeSendVo>> page(@RequestBody ReqPage<NodeSendVo> reqPage) {
        Page<NodeSendVo> page = new Page<>(reqPage.getPageNumber(), reqPage.getPageSize());
        return R.ok(nodeSendService.page(page));
    }

}
