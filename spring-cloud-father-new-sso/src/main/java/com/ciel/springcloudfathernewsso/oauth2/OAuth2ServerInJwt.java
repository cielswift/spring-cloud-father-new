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
     *------------------------------------------------------------------------------------------------
     *
     *  (A) 用户访问客户端，后者将前者导向认证服务器。
     *
     * （B）用户选择是否给予客户端授权。
     *
     * （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
     *
     * （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
     *
     * （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
     *
     *  A步骤中，客户端申请认证的URI，包含以下参数：
     * response_type：表示授权类型，必选项，此处的值固定为"code"
     * client_id：表示客户端的ID，必选项
     * redirect_uri：表示重定向URI，可选项
     * scope：表示申请的权限范围，可选项
     * state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。
     *
     * C步骤中，服务器回应客户端的URI，包含以下参数：
     * code：表示授权码，必选项。该码的有效期应该很短，通常设为10分钟，客户端只能使用该码一次，否则会被授权服务器拒绝
     * 。该码与客户端ID和重定向URI，是一一对应关系。
     * state：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数
     *
     * D步骤中，客户端向认证服务器申请令牌的HTTP请求，包含以下参数：
     * grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"。
     * code：表示上一步获得的授权码，必选项。
     * redirect_uri：表示重定向URI，必选项，且必须与A步骤中的该参数值保持一致。
     * client_id：表示客户端ID，必选项。
     *
     * E步骤中，认证服务器发送的HTTP回复，包含以下参数：
     * access_token：表示访问令牌，必选项。
     * token_type：表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型。
     * expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
     * refresh_token：表示更新令牌，用来获取下一次的访问令牌，可选项。
     * scope：表示权限范围，如果与客户端申请的范围一致，此项可省略。
     *
     *--------------------------------------------------
     * 更新令牌
     * 如果用户访问的时候，客户端的"访问令牌"已经过期，则需要使用"更新令牌"申请一个新的访问令牌
     * 客户端发出更新令牌的HTTP请求，包含以下参数：
     * granttype：表示使用的授权模式，此处的值固定为"refreshtoken"，必选项。
     * refresh_token：表示早前收到的更新令牌，必选项。
     * scope：表示申请的授权范围，不可以超出上一次申请的范围，如果省略该参数，则表示与上一次一致。
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
