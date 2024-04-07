package ai.hajimebot.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class SaTokenConfig implements WebMvcConfigurer {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        // Register a Sa-Token interceptor and define detailed authentication rules
        registry.addInterceptor(new SaInterceptor(handler -> {
            SaRouter.match("/**").check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**").excludePathPatterns(ignoreUrls);
    }


}
