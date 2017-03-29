package com.pgs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mmalek on 2/14/2017.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*//**").addResourceLocations("/");
        registry.addResourceHandler("**/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");

        registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/documentation/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/documentation/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
        registry.addRedirectViewController("/documentation/", "/documentation/swagger-ui.html");

    }
}
