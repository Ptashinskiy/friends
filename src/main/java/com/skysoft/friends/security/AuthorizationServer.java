package com.skysoft.friends.security;

import com.skysoft.friends.security.build_token.CustomTokenEnhancer;
import com.skysoft.friends.security.build_token.Client;
import com.skysoft.friends.security.build_token.CustomPasswordEncoder;
import com.skysoft.friends.security.read_token.CustomTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import static java.util.Arrays.asList;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private CustomTokenEnhancer tokenEnhancer;
    private CustomTokenConverter tokenConverter;
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServer(AuthenticationManager authenticationManager, CustomTokenEnhancer tokenEnhancer,
                               CustomTokenConverter tokenConverter, CustomPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenEnhancer = tokenEnhancer;
        this.tokenConverter = tokenConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(Client.getWebClient().getName())
                .scopes("read", "write", "trust")
                .secret(passwordEncoder.encode(Client.getWebClient().getPassword()))
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(60 * 60 * 24 * 30)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 30 * 6);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(asList(tokenEnhancer, accessTokenConverter()));
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .pathMapping("/oauth/token", "/login")
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
