package io.github.mockup.zenith.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.mockup.zenith.core.interceptor.AccessTokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AccessTokenInterceptor interceptor;

    public WebConfig(AccessTokenInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/**")     // すべてのリクエストに適用
            .excludePathPatterns("/login", "/auth/**", "/css/**", "/js/**"); // 除外ルート
    }
}