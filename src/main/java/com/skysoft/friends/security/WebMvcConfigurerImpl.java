package com.skysoft.friends.security;

import com.skysoft.friends.security.read_token.HandlerMethodArgumentResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    private HandlerMethodArgumentResolverImpl handlerMethodArgumentResolver;

    @Autowired
    public WebMvcConfigurerImpl(HandlerMethodArgumentResolverImpl handlerMethodArgumentResolver) {
        this.handlerMethodArgumentResolver = handlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(handlerMethodArgumentResolver);
    }
}
