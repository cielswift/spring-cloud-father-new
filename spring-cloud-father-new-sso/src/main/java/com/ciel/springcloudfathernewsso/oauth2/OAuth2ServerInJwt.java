package com.ciel.springcloudfathernewsso.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerInJwt extends AuthorizationServerConfigurerAdapter {


    /**
     * 授权服务器 基于jwt
     *
     * 同jdbc差不多,返回的是token
     *
     * {
     *     "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nY2xvdWQtcHJvZHVjZXIiXSwidXNlcl9uYW1lIjoieGlhcGVpeGluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU4MDY0NzU0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9hZG1pbiIsInN5c19hZGQiXSwianRpIjoiZjFjY2QwNWMtNTZhOS00OWFmLTgxNTgtMDljZDhkYzlmZGRmIiwiY2xpZW50X2lkIjoieGlhcGVpeGluIn0.PMmzM6r60sQ94e6U9kjdnBwZVXIMhToOBnYI8sMZPo4",
     *     "token_type": "bearer",
     *     "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nY2xvdWQtcHJvZHVjZXIiXSwidXNlcl9uYW1lIjoieGlhcGVpeGluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImYxY2NkMDVjLTU2YTktNDlhZi04MTU4LTA5Y2Q4ZGM5ZmRkZiIsImV4cCI6MTU4MzIzMjM0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9hZG1pbiIsInN5c19hZGQiXSwianRpIjoiYTA1YjRjZDktMWIxMS00ZWI5LWI1MDMtMzQ1OTE1YmNhMmRiIiwiY2xpZW50X2lkIjoieGlhcGVpeGluIn0.XQxkTj6bc8XztgIPtgfra96OT_uZqnT5LYvajfsToWw",
     *     "expires_in": 7199,
     *     "scope": "read write",
     *     "jti": "f1ccd05c-56a9-49af-8158-09cd8dc9fddf"
     * }
     *
     */

    @Autowired
    protected DataSource dataSource;

    /**
     * 令牌存储策略
     */
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("xia123"); //密钥
        return accessTokenConverter;
    }

    /**
     *客户端服务详情
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     *授权码模式存储策略
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     授权信息保存策略
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices(){

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(jdbcClientDetailsService()); //客户端服务详情
        tokenServices.setSupportRefreshToken(true); //支持刷新
        tokenServices.setTokenStore(tokenStore()); //令牌存储策略

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()));

        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        tokenServices.setAccessTokenValiditySeconds(7200); //有效期
        tokenServices.setRefreshTokenValiditySeconds(25920); //刷新时间

        return tokenServices;
    }

    /**
     *客户端保存策略
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 检测token的策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();

        security.checkTokenAccess("permitAll()") //检验必须认证; isAuthenticated()
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

}
