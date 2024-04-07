package ai.hajimebot.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.server.HttpServerResponse;
import ai.hajimebot.config.FileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
@ApiIgnore
public class FileController {

    @Autowired
    private FileConfig fileConfig;

    @RequestMapping("/downFile/{fileName}")
    @SaIgnore
    public String downFile(@PathVariable String fileName, HttpServerResponse response) {
//        FileUtil
        try {
            String path = fileConfig.getPath() + File.separator + fileName;
            File file = FileUtil.file(path);
            // read_into_the_stream
            FileInputStream inputStream = new FileInputStream(file);
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            OutputStream outputStream = response.getOut();
            byte[] b = new byte[1024];
            int len;
            //A certain number of bytes are read from the input stream and stored in a buffer byte array,
            // returning -1 at the end of the read
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
