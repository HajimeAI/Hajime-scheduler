package ai.hajimebot.controller;

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
import ai.hajimebot.entity.UserVo;
import ai.hajimebot.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 *  controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/userVo")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * save
     *
     * @param userVo 
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public boolean save(@RequestBody UserVo userVo) {
        return userService.save(userVo);
    }

    /**
     * Delete based on the primary key
     *
     * @param id
     * @return {@code true} delete succeeded, {@code false} delete failed
     */
    @DeleteMapping("remove/{id}")
    @ApiOperation("delete")
    public boolean remove(@PathVariable Serializable id) {
        return userService.removeById(id);
    }

    /**
     * Updates based on the primary key.
     *
     * @param userVo 
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PutMapping("update")
    @ApiOperation("update")
    public boolean update(@RequestBody UserVo userVo) {
        return userService.updateById(userVo);
    }

    /**
     * Query all.
     *
     * @return All data
     */
    @GetMapping("list")
    @ApiOperation("query all")
    public List<UserVo> list() {
        return userService.list();
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("get info")
    public UserVo getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }

}
