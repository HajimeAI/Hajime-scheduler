package ai.hajimebot.controller;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import ai.hajimebot.entity.MyDeviceVo;
import ai.hajimebot.service.MyDeviceService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.List;

/**
 * controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController

@RequestMapping("/myDeviceVo")
public class MyDeviceController {

    @Autowired
    private MyDeviceService myDeviceService;

    /**
     * save
     *
     * @param myDeviceVo
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public R<?> save(@RequestBody @ApiParam("") MyDeviceVo myDeviceVo) {
        return myDeviceService.insertData(myDeviceVo);
    }

    /**
     * Delete based on the primary key
     *
     * @param id
     * @return {@code true} delete succeeded, {@code false} delete failed
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("delete")
    public boolean remove(@PathVariable @ApiParam("Primary Key") Serializable id) {
        return myDeviceService.removeById(id);
    }

    /**
     * Updates based on the primary key.
     *
     * @param myDeviceVo
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public boolean update(@RequestBody @ApiParam("Primary Key") MyDeviceVo myDeviceVo) {
        return myDeviceService.updateById(myDeviceVo);
    }

    /**
     * Query all.
     *
     * @return All data
     */
    @GetMapping("list")
    @ApiOperation("query all")
    public List<MyDeviceVo> list() {
        return myDeviceService.list();
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("getInfo")
    public MyDeviceVo getInfo(@PathVariable @ApiParam("Primary Key") Serializable id) {
        return myDeviceService.getById(id);
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
        return myDeviceService.getList(reqPage);
    }

}
