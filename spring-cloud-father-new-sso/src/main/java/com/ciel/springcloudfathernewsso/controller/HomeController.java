package com.ciel.springcloudfathernewsso.controller;

import com.ciel.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    protected IUserService userService;

    @GetMapping("/test")
    @PreAuthorize("hasRole('admin')")
    public Object test() {

        userService.testTransaction();

        userService.testAop();

        return Map.of("c", "c2");
    }

//    @Secured("ROLE_BOSS")  //基于注解的访问控制,参数可以以ROLE_开头。
//    @RolesAllowed("ROLE_BOSS")
 //    @PermitAll允许所有访问
//    @DenyAll 拒绝所有访问
//   @PreAuthorize("@myServiceImpl.hasPermission(#request,#authentication)") //自定义认证

    /**
     * 表示方法或类执行结束后判断权限，此注解很少被使用到。
     */
    //@PostAuthorize("hasAuthority('USER')")

    //表示访问方法或类在执行之前先判断权限,参数和access()方法参数取值相同，都是权限表达式。
    //自定义判断必须返回true或false; 在注解上需要加#获取请求参数, 配置中和页面上不用;

    //比如@PreAuthorize("principal.username.equals(#username)") 限制只能查询自己的信息

//    使用@PreFilter和@PostFilter可以对集合类型的(参数)或(返回值)进行过滤。使用@PreFilter和@PostFilter时，
//    Spring Security将移除使对应表达式的结果为false的元素。

    //@PostFilter("filterObject.id%2==0"） //过滤返回结果,返回结果是一个集合 ;filterObject表示集合的当前对象
    //@PreFilter(filterTarget="ids", value="filterObject%2==0") //对参数进行过滤,参数是一个集合,参数名叫ids;

    @GetMapping("/")
    @PreAuthorize("hasRole('admin') and hasPermission('/','sys_add')")
    public Map index(HttpServletRequest request, Authentication authentication) {
        return Map.of("MSG", "单点登录服务器");
    }

    @GetMapping("/say")
    @PreAuthorize("hasAnyAuthority('sys_ngngngng' , 'sys_sasasasa')") //any任何一个都可以
    public Map say() {
        return Map.of("name", "say");
    }

    @GetMapping("/oth")
    @PreAuthorize("hasPermission('sys_add' , 'sys_del')")  //自定义认证
    public Map oth() {
        return Map.of("name", "oth");
    }

    //    hasRole([role])	当前账户有指定角色时返回true, 默认情况下，角色都是以ROLE_开头，当然也可以在修改DefaultWebSecurityExpressionHandler中修改defaultRolePrefix自定义角色前缀
//* hasAnyRole([role1,role2])	当前账户有指定角色中的任意一个时返回true, 默认情况下，角色都是以ROLE_开头，当然也可以在修改DefaultWebSecurityExpressionHandler中修改defaultRolePrefix自定义角色前缀
//    hasAuthority([authority])	当前账户有指定权限时返回true
//    hasAnyAuthority([authority1,authority2])	当前账户有指定权限中任何一个时返回true
//    principal	允许当前用户直接访问的对象主体
//    authentication	允许直接访问从SecurityContext获得的当前身份验证对象
//    permitAll	允许所有
//    denyAll	拒绝所有
//    isAnonymous()	是否匿名用户
//    isRememberMe()	当前是否被记住
//* isAuthenticated()	是否已经登录
//    isFullyAuthenticated()	是否已经登录 或 被记住
//    hasIpAddress([ip address])	IP地址是否是？？？


}
