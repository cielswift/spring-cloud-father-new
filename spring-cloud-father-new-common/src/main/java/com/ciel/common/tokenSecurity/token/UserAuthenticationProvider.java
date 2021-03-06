package com.ciel.common.tokenSecurity.token;

import com.ciel.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义登录验证
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private IUserService userService;

    /**
     * 是否支持这个 Authentication 针对什么样的认证进行校验; 原本是UsernamePasswordAuthenticationToken;
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在

        UserDetails userDetails = userDetailService.loadUserByUsername(userName);

        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (!userDetails.isEnabled()){
            throw new LockedException("该用户已被冻结");
        }

        // 进行登录
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

}