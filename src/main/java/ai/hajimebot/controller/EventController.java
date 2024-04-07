package ai.hajimebot.controller;

import ai.hajimebot.entity.EventVo;
import ai.hajimebot.global.R;
import ai.hajimebot.service.EventService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 *  controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/eventVo")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * save
     *
     * @param eventVo 
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public R<?> save(@RequestBody @ApiParam("") EventVo eventVo) {
        boolean status =  eventService.save(eventVo);
        if(status) {
            return R.ok("add success");
        }
        return R.fail("add failed");
    }

    /**
     * Delete based on the primary key
     *
     * @param id
     * @return {@code true} delete succeeded, {@code false} delete failed
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("delete")
    public R<?> remove(@PathVariable @ApiParam("Primary Key") Serializable id) {
        boolean status = eventService.removeById(id);
        if(status) {
            return R.ok("delete success");
        }
        return R.fail("delete failed");
    }

    /**
     * Updates based on the primary key.
     *
     * @param eventVo 
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public R<?> update(@RequestBody @ApiParam("Primary Key") EventVo eventVo) {
        boolean status = eventService.updateById(eventVo);
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
    public R<List<EventVo>> list() {
        return R.ok(eventService.list());
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("getInfo")
    public EventVo getInfo(@PathVariable @ApiParam("Primary Key") Serializable id) {
        return eventService.getById(id);
    }

    /**
     * Paginated queries.
     *
     */
    @GetMapping("page")
    @ApiOperation("Paginated queries")
    public Page<EventVo> page(@ApiParam("page info") Page<EventVo> page) {
        return eventService.page(page);
    }

}
