package tw.com.rex.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

/**
 * JWT 非對稱加密 authorization server configuration
 *
 * @author Rex Yu
 */
@Configuration
@EnableAuthorizationServer
@Profile("keystore")
public class Oauth2KeystoreAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InfoTokenEnhancer infoTokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        builder
                // client id
                .withClient("oauth")
                // client 密鑰 (加密過的)
                .secret("$2a$10$EyohJlLTb7tx/TkVBOXc2eojIayx3Jz1IwUUNfi8CTKKWk5trzsoS")
                // 授權後可用的 resource id
                .resourceIds("oauth-resource")
                // 可用的授權模式
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                // 可授權的角色
                .authorities("ROLE_ADMIN", "ROLE_USER")
                // 授權範圍
                .scopes("all")
                // token 有效時間
                .accessTokenValiditySeconds(6000)
                //刷新 token 有效時間
                .refreshTokenValiditySeconds(6000)
                // client 回調網址
                .redirectUris("https://www.google.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 不加這段密碼模式無法使用
                .authenticationManager(authenticationManager)
                // 不加這段無法刷新 token
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(tokenEnhancerChain())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 不加這段 resource server 無法存取該資源權限 (403) (/oauth/check_token)
                .checkTokenAccess("isAuthenticated()")
                // 開放公鑰獲取 (/oauth/token_key)
                .tokenKeyAccess("isAuthenticated()");
    }

    /**
     * JWT 儲存 token
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * token 轉換器，非對稱密鑰加密
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.keystore"), "111111".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    /**
     * 加載 JWT 訊息擴充
     *
     * @return TokenEnhancerChain
     */
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoTokenEnhancer, jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

}
