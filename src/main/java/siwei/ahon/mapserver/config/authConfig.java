package siwei.ahon.mapserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import siwei.ahon.mapserver.interceptor.AuthInterceptor;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class authConfig extends WebMvcConfigurationSupport {
    @Resource
    AuthInterceptor authInterceptor;

//    @Resource
//    CorsInterceptor cors;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/mapserver/login");
        excludePath.add("/mapserver/register");
        excludePath.add("/mapserver/verifyToken");
        excludePath.add("/widescreenserver/**");
        excludePath.add("/panelserver/**");

//        registry.addInterceptor(cors).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }






}

