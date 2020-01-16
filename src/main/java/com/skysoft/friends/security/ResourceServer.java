package com.skysoft.friends.security;

import com.skysoft.friends.security.build_token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServer extends ResourceServerConfigurerAdapter {

    private JwtAccessTokenConverter tokenConverter;
    private CustomTokenEnhancer tokenEnhancer;

    @Autowired
    public ResourceServer(JwtAccessTokenConverter tokenConverter, CustomTokenEnhancer tokenEnhancer) {
        this.tokenConverter = tokenConverter;
        this.tokenEnhancer = tokenEnhancer;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/access/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/access/confirm").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(defaultTokenServices());
        resources.tokenServices(defaultTokenServices()).authenticationManager(authenticationManager);
    }

    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(tokenEnhancer);
        defaultTokenServices.setTokenStore(new JwtTokenStore(tokenConverter));
        return defaultTokenServices;
    }
}
