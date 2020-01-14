package com.skysoft.friends.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import static java.util.Arrays.asList;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private CustomTokenEnhancer tokenEnhancer;
    private CustomTokenConverter tokenConverter;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServer(AuthenticationManager authenticationManager, CustomTokenConverter tokenConverter,
                               CustomTokenEnhancer tokenEnhancer, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenConverter = tokenConverter;
        this.tokenEnhancer = tokenEnhancer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(Client.getWebClient().getName())
                .scopes("read", "write", "trust")
                .secret(passwordEncoder.encode(Client.getWebClient().getPassword()))
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(2592000)
                .refreshTokenValiditySeconds(15552000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(asList(tokenEnhancer, accessTokenConverter()));
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .pathMapping("/oauth/token", "/oauth/login")
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("4h;P:Gwge#VthS");
        accessTokenConverter.setAccessTokenConverter(tokenConverter);
        return accessTokenConverter;
    }
}
