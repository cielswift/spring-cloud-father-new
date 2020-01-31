package com.ciel.springcloudfathernewsso.security.token;

import com.ciel.api.IRoleService;
import com.ciel.api.IUserService;
import com.ciel.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        com.ciel.entity.User user = userService.findByUserName(userName);

        if (user != null) {

            List<Role> roles = userService.getRolesByUser(user);

            List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

            roles.forEach(t -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_".concat(t.getName())));
                roleService.getPermissionsByRole(t).stream().forEach(j -> authorities.add(new SimpleGrantedAuthority(j.getName())));
            });

            CustomUser customUser = new CustomUser(userName, user.getPassword(), authorities);
            customUser.setId(user.getId());
            customUser.setName(user.getName());
            return customUser;

        } else {
            throw new UsernameNotFoundException("找不到此用户");
        }

    }
}
