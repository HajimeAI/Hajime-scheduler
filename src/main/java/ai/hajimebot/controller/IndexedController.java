package ai.hajimebot.controller;

import ai.hajimebot.config.FileConfig;
import ai.hajimebot.entity.IndexedVo;
import ai.hajimebot.entity.table.IndexedVoTableDef;
import ai.hajimebot.global.R;
import ai.hajimebot.global.ReqPage;
import ai.hajimebot.service.IndexedService;
import ai.hajimebot.service.WebSocketService;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * controller。
 *
 * @author xc.deng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/indexedVo")
@Slf4j
@Api(tags = "Indexed Interface")
public class IndexedController {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private IndexedService indexedService;

    @Autowired
    private WebSocketService webSocketService;

    /**
     * save
     *
     * @param indexedVo
     * @return {@code true} added successfully, {@code false} added failed
     */
    @PostMapping("save")
    @ApiOperation("save")
    public R<?> save(@RequestBody IndexedVo indexedVo) {
        boolean status = indexedService.save(indexedVo);
        if (status) {
            webSocketService.sendIndexAllMessage();
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
        boolean status = indexedService.removeById(id);
        if (status) {
            webSocketService.sendIndexAllMessage();
            return R.ok("delete success");
        }
        return R.fail("delete failed");
    }

    /**
     * Updates based on the primary key.
     *
     * @param indexedVo
     * @return {@code true} update succeeded, {@code false} update failed
     */
    @PostMapping("update")
    @ApiOperation("update")
    public R<?> update(@RequestBody IndexedVo indexedVo) {
        boolean status = indexedService.updateById(indexedVo);
        if (status) {
            webSocketService.sendIndexAllMessage();
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
    public R<?> list() {
        return R.ok(indexedService.list());
    }

    /**
     * Get detailed information based on the primary key.
     *
     * @param id
     * @return Details
     */
    @GetMapping("getInfo/{id}")
    @ApiOperation("get info")
    public IndexedVo getInfo(@PathVariable Serializable id) {
        return indexedService.getById(id);
    }

    /**
     * Paginated queries.
     *
     * @param reqPage Pagination objects
     * @return Pagination objects
     */
    @PostMapping("page")
    @ApiOperation("Paginated queries")
    public R<?> page(@RequestBody ReqPage<IndexedVo> reqPage) {
        Page<IndexedVo> page = new Page<>(reqPage.getPageNumber(), reqPage.getPageSize());
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (reqPage.getData() != null && !StrUtil.isBlank(reqPage.getData().getName())) {
            queryWrapper.where(IndexedVoTableDef.INDEXED_VO.NAME.like(reqPage.getData().getName()));

        }

        return R.ok(indexedService.page(page, queryWrapper));
    }

    @PostMapping("/upload")
    @ApiOperation("file upload")
    public R<?> upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        File folder = new File(fileConfig.getPath());
        //Determine whether the file exists
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        try {
            //File save
            file.transferTo(new File(folder.getAbsolutePath(), oldName));
            //Get the request agreement
            String s = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + oldName;
            Map<String, Object> map = new HashMap<>();
            map.put("fileUrl", oldName);
            map.put("url", s);
            return R.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.fail("upload failed");
    }
}
