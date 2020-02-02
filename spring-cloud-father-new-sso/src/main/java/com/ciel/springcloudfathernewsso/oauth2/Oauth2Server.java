//package com.ciel.springcloudfathernewsso.oauth2;
//
//import com.ciel.api.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.approval.ApprovalStore;
//import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//
//@Configuration
//@EnableAuthorizationServer
//public class Oauth2Server extends AuthorizationServerConfigurerAdapter {
//
//    /**
//     * 授权服务器 基于jdbc
//     *
//     * get http://127.0.0.1:3400/sso/oauth/authorize?client_id=xiapeixin&response_type=code&redirect_uri=https://www.baidu.com/&Authentication=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqd3RfdG9rZW4iLCJhdWQiOiJ1c2VyIiwiaXNzIjoic3NvX3NlcnZlciIsImJvZHkiOiJ7XCJwYXNzd29yZFwiOlwiJDJhJDEwJGJiSVZ0OWRuRWFremhYM1FNcDZSTHVvY0ZDeGo0UUJ2NWVmMWUxV0lTMUQuRGF0RnZGRW5HXCIsXCJhdXRob3JpdGVzXCI6W3tcImF1dGhvcml0eVwiOlwiUk9MRV9hZG1pblwifSx7XCJhdXRob3JpdHlcIjpcInN5c19hZGRcIn1dLFwibmFtZVwiOlwieGlhcGVpeGluXCIsXCJpZFwiOjE1Nzk4OTMwMzYzNzIsXCJ1c2VybmFtZVwiOlwieGlhcGVpeGluXCJ9IiwiZXhwIjoxNTgwNTUwODI1LCJpYXQiOjE1ODA1NDcyMjV9.lipMY0buGfdxJBJOkGEMM-7lGctlDTTDs7uFmR36feM
//     *
//     * 会跳转到edirect_uri, 并携带授权码ttps://www.baidu.com/?code=JGMS3h
//     * 注意: 从页面上点击Authorize不带token 需要自己debug添加;
//     *
//     * 拿到授权码后
//     *
//     *
//     * post http://127.0.0.1:3400/sso/oauth/token?client_id=xiapeixin&client_secret=123456&grant_type=authorization_code&code=8wyYdK&redirect_uri=https://www.baidu.com/
//     *
//     * 由于这里单点登录服务器和oauth2认证服务器在一台服务器上,因此要求:client_id=xiapeixin client_secret=123456
//     *        客户端id 和密码 等于 用户 密码
//     *
//     * grant_type=authorization_code 授权码模式
//     * code=8wyYdK  授权码 , redirect_uri=https://www.baidu.com/ 回调
//     *  获得令牌
//     *      {
//     *     "access_token": "8eb03748-6123-43c7-b376-8213a36c9a56",
//     *     "token_type": "bearer",
//     *     "refresh_token": "be416d30-7d61-480d-b389-5c257dc98056",
//     *     "expires_in": 29,
//     *     "scope": "read write"
//     *      }
//     *
//     *-------------------------------------------------------------------------------------------
//     * 简化模式
//     *
//     * post http://127.0.0.1:3400/sso/oauth/authorize?client_id=xiapeixin&response_type=token&redirect_uri=https://www.baidu.com/
//     *
//     * response_type=token
//     *
//     * 给回调地址传参,直接令牌 https://www.baidu.com/#access_token=61651c18-bfca-4787-bfe2-105a0a60f09d&token_type=bearer&expires_in=29&scope=read%20write
//     *
//     *--------------------------------------------------------------------------------------------------
//     * 密码模式
//     *
//     * post http://127.0.0.1:3400/sso/oauth/token?client_id=xiapeixin&client_secret=123456&grant_type=password&code=EmcDVt&redirect_uri=https://www.baidu.com&username=xiapeixin&password=123456
//     *
//     * grant_type=password
//     *
//     * {
//     *     "access_token": "f711e7d5-22e4-469a-b2ee-59e7fc21aebd",
//     *     "token_type": "bearer",
//     *     "refresh_token": "c21631b3-9ebc-489d-835d-8458af42116c",
//     *     "expires_in": 29,
//     *     "scope": "read write"
//     * }
//     *
//     * ---------------------------------------------------------------------------------------------------------
//     * 客户端模式
//     *
//     * post http://127.0.0.1:3400/sso/oauth/token?client_id=xiapeixin&client_secret=123456&grant_type=client_credentials&code=EmcDVt&redirect_uri=https://www.baidu.com&username=xiapeixin&password=123456
//     *
//     * grant_type=client_credentials
//     *
//     *  {
//     *     "access_token": "fdc042f0-5a93-4dd5-be3e-5ef35d562d16",
//     *     "token_type": "bearer",
//     *     "expires_in": 29,
//     *     "scope": "read write"
//     * }
//     *
//     */
//
//
//
//    /**数据库连接对象
//     */
//    @Autowired
//    protected DataSource dataSource;
//
//    /**
//     * 授权码模式专业对象
//     */
//    @Autowired
//    protected AuthenticationManager authenticationManager;
//
//    /**
//     * 认证业务对象
//     */
//    protected IUserService userService;
//
//    /**
//     *客户端服务详情
//     */
//    @Bean
//    public JdbcClientDetailsService jdbcClientDetailsService(){
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    /**
//     * token保存策略
//     */
//    @Bean
//    public TokenStore tokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }
//
//    /**
//     授权信息保存策略
//     */
//    @Bean
//    public ApprovalStore approvalStore(){
//        return new JdbcApprovalStore(dataSource);
//    }
//
//    /**
//     *
//     授权码模式数据来源
//     */
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new JdbcAuthorizationCodeServices(dataSource);
//    }
//
//    /**
//     * 客户端详情服务
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//       clients.withClientDetails(jdbcClientDetailsService());
//    }
//
//    /**
//     * 检测token的策略
//     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.allowFormAuthenticationForClients();
//
//        security.checkTokenAccess("permitAll()") //检验必须认证; isAuthenticated()
//                .tokenKeyAccess("permitAll()");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints.approvalStore(approvalStore())
//                .authenticationManager(authenticationManager)
//                .authorizationCodeServices(authorizationCodeServices())
//                .tokenStore(tokenStore())
//                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
//
//
//        // 配置TokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds(30); // 30天
//        endpoints.tokenServices(tokenServices);
//    }
//
//}
//
//
