package ai.hajimebot.controller;

import com.mybatisflex.core.paginate.Page;
import ai.hajimebot.global.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ai.hajimebot.entity.LogVo;
import ai.hajimebot.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;
import java.util.List;

/**
 *  controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/logVo")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * save
     *
     * @param logVo 
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public boolean save(@RequestBody @ApiParam("") LogVo logVo) {
        return logService.save(logVo);
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
        return logService.removeById(id);
    }

    /**
     * Updates based on the primary key.
     *
     * @param logVo 
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public boolean update(@RequestBody @ApiParam("Primary Key") LogVo logVo) {
        return logService.updateById(logVo);
    }

    /**
     * Query all.
     *
     * @return All data
     */
    @GetMapping("list")
    @ApiOperation("query all")
    public List<LogVo> list() {
        return logService.list();
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("getInfo")
    public LogVo getInfo(@PathVariable @ApiParam("Primary Key") Serializable id) {
        return logService.getById(id);
    }

    /**
     * Paginated queries.
     *
     * @param page Pagination objects
     * @return Pagination objects
     */
    @GetMapping("page")
    @ApiOperation("Paginated queries")
    public Page<LogVo> page(@ApiParam("page info") Page<LogVo> page) {
        return logService.page(page);
    }

    @GetMapping("getList")
    @ApiOperation("get log")
    public R<?> getList(String content) {
        return logService.getList(content);
    }

}
