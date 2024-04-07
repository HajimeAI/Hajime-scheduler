package ai.hajimebot.config;

import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class WebMvcConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = new File("/files/").getAbsolutePath();

        // external Access Address
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + absolutePath)
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES));
    }
}
