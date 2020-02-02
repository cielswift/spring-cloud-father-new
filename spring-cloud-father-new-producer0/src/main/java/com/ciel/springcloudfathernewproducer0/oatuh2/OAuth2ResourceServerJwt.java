package com.ciel.springcloudfathernewproducer0.oatuh2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer //整合oauth2
public class OAuth2ResourceServerJwt extends ResourceServerConfigurerAdapter {

    /**
     * 检验token
     * http://127.0.0.1:3400/sso/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nY2xvdWQtcHJvZHVjZXIiXSwidXNlcl9uYW1lIjoieGlhcGVpeGluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU4MDY2MTE1MiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9hZG1pbiIsInN5c19hZGQiXSwianRpIjoiZWU0NDFhMjAtNzJjNy00NzAwLTkyZTItYTA1ZWVlZjU1OGM3IiwiY2xpZW50X2lkIjoieGlhcGVpeGluIn0.3xIMWQOtOQ8YyQfRv01a31VXOUyjSjExCzi2LAHEhnU
     *
     * {
     *     "aud": [
     *         "springcloud-producer"
     *     ],
     *     "user_name": "xiapeixin",
     *     "scope": [
     *         "read",
     *         "write"
     *     ],
     *     "active": true,
     *     "exp": 1580661152,
     *     "authorities": [
     *         "ROLE_admin",
     *         "sys_add"
     *     ],
     *     "jti": "ee441a20-72c7-4700-92e2-a05eeef558c7",
     *     "client_id": "xiapeixin"
     * }
     *
     */
}
