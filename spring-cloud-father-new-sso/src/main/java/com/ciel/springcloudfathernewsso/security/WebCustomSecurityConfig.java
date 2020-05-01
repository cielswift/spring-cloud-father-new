package com.ciel.springcloudfathernewsso.security;

import com.ciel.api.IUserService;
import com.ciel.common.tokenSecurity.handler.*;
import com.ciel.common.tokenSecurity.token.IpAuthenticationProvider;
import com.ciel.common.tokenSecurity.token.UserAuthenticationProvider;
import com.ciel.common.tokenSecurity.token.UserPermissionEvaluator;
import com.ciel.springcloudfathernewsso.security.filter.CustomAuthenticationFilter;
import com.ciel.springcloudfathernewsso.security.filter.IpAuthenticationProcessingFilter;
import com.ciel.springcloudfathernewsso.security.filter.JWTAuthenticationTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringSecurity配置类
 *
 *   securedEnabled : springsecurity 内部权限注解开关 ;对应 @Secured 注解
 *   prePostEnabled : spring指定的权限注解开关 :对应@PreAuthorize
 *   jsr250Enabled : 开启java对权限注解的支持 对应@RolesAllowed
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class WebCustomSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     *
     * AuthenticationManager（接口）是认证相关的核心接口，也是发起认证的出发点，因为在实际需求中，
     * 我们可能会允许用户使用用户名 + 密码登录，同时允许用户使用邮箱 + 密码，手机号码 + 密码登录，甚至，
     * 可能允许用户使用指纹登录（还有这样的操作？没想到吧），所以说 AuthenticationManager 一般不直接认证，
     * AuthenticationManager 接口的常用实现类 ProviderManager 内部会维护一个 List<AuthenticationProvider> 列表，
     * 存放多种认证方式，实际上这是委托者模式的应用（Delegate）。也就是说，核心的认证入口始终只有一个：AuthenticationManager
     * ，不同的认证方式：用户名 + 密码（UsernamePasswordAuthenticationToken），邮箱 + 密码，手机号码 + 密码登录则对应了三个
     * AuthenticationProvider。这样一来四不四就好理解多了？熟悉 shiro 的朋友可以把 AuthenticationProvider 理解成 Realm。
     * 在默认策略下，只需要通过一个 AuthenticationProvider 的认证，即可被认为是登录成功。
     *
     * ProviderManager 中的 List，会依照次序去认证，认证成功则立即返回，若认证失败则返回 null，下一个 AuthenticationProvider 会继续尝试认证
     * ，如果所有认证器都无法认证成功，则 ProviderManager 会抛出一个 ProviderNotFoundException 异常。
     *
     */

    /**
     * securityd的过滤器执行链都在DefaultSecurityFilterChain中
     *
     * FilterSecurityInterceptor 授权过滤器
     */

    /**
     * spring security的角色继承
     * ROLE_dba具备 ROLE_admin的所有权限，而 ROLE_admin则具备 ROLE_user的所有权限，继承与继承之间用一个空格隔开
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_dba > ROLE_admin \n ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 自定义ip辑验证器
     */
    @Autowired
    private IpAuthenticationProvider ipAuthenticationProvider;


    /**
     * 加密方式
     *
     * @Author Sans
     * @CreateTime 2019/10/1 14:00
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }


    @Override //不经过security的过滤器
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/**");
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);

        auth.authenticationProvider(ipAuthenticationProvider); //加入ip认证器
    }

    /**
     * 获取manager,整合oauth2需要这个对象
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    // 配置登录端点
    @Bean
    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint
                ("/ipLogin");
        return loginUrlAuthenticationEntryPoint;
    }

    /**
     * 配置security的控制逻辑
     *
     * authorizeRequests() 配置路径拦截，表明路径访问所对应的权限，角色，认证信息。
     * formLogin() 对应表单认证相关的配置
     * logout() 对应了注销相关的配置
     * httpBasic() 可以配置 basic 登录
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers("/login/**","/logout/**","/favicon.ico").permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/logout")
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();

        /**
         * 在用户未获认证的时候去请求一个需要认证后才能请求的数据，此时不给用户重定向，
         * 而是直接就返回一个 JSON，告诉用户这个请求需要认证之后才能发起，就不会有上面的事情了
         */
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {

                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                    //未登录异常
                Map<String, Object> resultData = new HashMap<>();
                resultData.put("code", "5000");
                resultData.put("msg", "访问失败!::"+authException.getMessage());

                out.write(new ObjectMapper().writeValueAsString(resultData));
                out.flush();
                out.close();

            }
        });


        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(jwtAuthenticationTokenFilter());

        //登录过滤器
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 注册 IpAuthenticationProcessingFilter  注意放置的顺序 这很关键
        http.addFilterAt(ipAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);



//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/ipLogin").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/ipLogin")
//                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint());
    }



    @Autowired
    @Qualifier("redisTemplateString")
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setRedisTemplate(redisTemplate);
        filter.setUserService(userService);
        return filter;
    }

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JWTAuthenticationTokenFilter tokenFilter = new JWTAuthenticationTokenFilter(authenticationManager());
        tokenFilter.setRedisTemplate(redisTemplate);
        tokenFilter.setUserService(userService);
        return tokenFilter;
    }

    public IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter() throws Exception {
        IpAuthenticationProcessingFilter ipfilter =
                new IpAuthenticationProcessingFilter(authenticationManager());

        // 为过滤器添加认证器
        //ipfilter.setAuthenticationManager(authenticationManager());
        // 重写认证失败时的跳转页面
        //ipfilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/ipLogin?error"));
        return ipfilter;
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception { //配置
//
//        //http.addFilter(); //手动添加一个过滤器 ,(必须是spring提供的过滤器)
//        // 如果我们创建的Filter没有在预先设置的Map集合中，那么就会抛出一个IllegalArgumentException异常，
//        // 并提示我们使用addFilterBefore或者addFilterAfter
//
//        http.addFilterAt(new JWTLoginFilter(super.authenticationManager(),ras), UsernamePasswordAuthenticationFilter.class);
//        http.addFilter(new JWTAuthenticationFilter(super.authenticationManager(),ras));
//        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用session;
//
//
//        //表单认证
//        http.formLogin()  // 设置表单登录，创建UsernamePasswordAuthenticationFilter拦截器 (FormLoginConfigurer)
//                .loginProcessingUrl("/log")			//设置登录请求url,需要执行UserDetailsServiceImpl
//                .loginPage("/login")				//设置自定义登录页面，使用绝对路径
//
////                .successForwardUrl("/hello")	//设置登陆成功后跳转的请求路径
////                .failureForwardUrl("/defeat")   //认证失败后跳转的url,
//
//                // 将登陆成功/ 失败跳转到对应的 url 的方法修改成登陆成功/ 失败后跳转到对应的 处理器类 的方法
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(new  MyFailHandler("/defeat"))
//
//                .usernameParameter("username")         //自定义设置认证表单中用户名的name属性
//                .passwordParameter("password");        //自定义设置认证表单中密码的password属性
//
//
//        http.httpBasic(); // 开启HTTP Basic，创建BasicAuthenticationFilter拦截器 (HttpBasicConfigurer)
//
//        //拦截: 相当于授权的过程
//        http.authorizeRequests()  // 拦截请求，创建了FilterSecurityInterceptor拦截器; (ExpressionUrlAuthorizationConfigurer)
//                .antMatchers("/login","/regis","/reg").permitAll()
//                //请求登录页面的请求不需要认证。有人都可以访问登录页面,一定要配置/login,不然无线重定向;
//
//                .antMatchers("/**/*.jpg").permitAll()   //放行所有为jpg格式的图片
//                .antMatchers(HttpMethod.GET,"/love/**","/css/**","/js/**","/images/**").permitAll()
//
////                ? 匹配一个字符
////                * 匹配0 个或多个字符
////                ** 匹配0 个或多个目
//
//                .regexMatchers(HttpMethod.POST,".+[.]jpg").permitAll() //使用正则表达式进行匹配,必须是post请求
//
//                .mvcMatchers("/demo").servletPath("/asso").permitAll()
//                //servletPath()中配置了servletPath 后，mvcMatchers()直接写SpringMVC 中@RequestMapping()中设置的路径即可。
//
//
//                //permitAll()表示不需要认证，随意访问。
//                //denyAll()表示所匹配的URL 都不允许被访问。
//                //anonymous()表示可以匿名访问匹配的URL。和permitAll()效果类似，只是设置为anonymous()的url 会执行filter 链中
//                //authenticated()表示所匹配的URL 都需要被认证才能访问
//                //fullyAuthenticated(),如果用户不是被remember me(记住我) 的，才可以访问。
//                //rememberMe() 被“remember me”的用户允许访问
//
//                /*------------------------------------------------------------------------------------------------------**/
//                // 权限
//                .antMatchers("/10.jpg").hasAuthority("USER") //设置具有USER 权限时才能访问。
//
//                .antMatchers("/main1.html").hasAnyAuthority("ADMIN","USER") //如果用户具备给定权限中某一个，就允许访问
//
//                .antMatchers("/main2.html").hasRole("abc") //如果用户具备给定角色就允许访问。否则出现403
//
////        在给用户赋予角色时角色需要以：ROLE_ 开头，后面添加角色名称。return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_abc"));
////        例如：ROLE_abc 其中abc 是角色名，ROLE_是固定的字符开头。
////        使用hasRole()时参数也只写abc 即可。否则启动报错
//
//                .antMatchers("/main.html").hasAnyRole("abC","abc","ABC")  //判断用户是否用户具备给定角色的任意一个，是就允许被访问
//
//                .antMatchers("/main.html").hasIpAddress("127.0.0.1")   //如果请求是指定的IP 就运行访问。
//
//                /*------------------------------------------------------------------------------------------------------**/
//                // acc表达式
//
//                //.antMatchers("/login.html").permitAll()   //登陆页面不需要被认证
//                .antMatchers("/login.html").access("permitAll")  //使用access实现拦截
//
//                //.antMatchers("/main1.html").hasRole("abc")	//判断用户是否具备给定角色,是就允许访问。
//                .antMatchers("/main1.html").access("hasRole(\"abc\")") //使用access实现角色权限验证
//
//                .antMatchers("/love").access("@myServiceImpl.hasPermission(request,authentication)")
//                //在access 中通过@bean 的id 名.方法(参数)的形式进行调用 ,配置类中修改如下
//
//
//                .anyRequest().authenticated();
//        //其他所有的请求都必须被认证。必须登录后才能访问。
//
////        在所有匹配规则中取所有规则的交集。配置顺序影响了之后授权效果，
////        越是具体的应该放在前面，越是笼统的应该放到后面。
//
//
//        //自定义异常处理页面-403
//        http.exceptionHandling()
//                .accessDeniedHandler(myAccessDeniedHandler);
//
//
//        //关闭csrf防护，类似于防火墙，不关闭上面的设置不会真正生效。 //关闭认证,让eureka连接
//        http.csrf().disable();
//
//
//        http.rememberMe()
//                //.rememberMeParameter("rememberMe")     //修改rememberMe前端复选框的name属性值, 默认为remember-me
//                //.tokenValiditySeconds(60)            	 //设置tokenValiditySeconds生效时间, 默认是2周,单位是秒
//                .userDetailsService(userDetailsService)		 //登录逻辑交给哪个对象
//                .tokenRepository(repository);
//
//        http.logout()
//                .logoutSuccessUrl("/login");	   //用户退出成功后 ,跳转的页面
//        //.logoutUrl("/logout")                //修改用户退出时,超链接的name属性值,一般不推荐修改
//        //会自动清除RememberMe中在数据库中持久化的口令 ,如果需要的话需要重新登陆并勾选
//
//    }
}