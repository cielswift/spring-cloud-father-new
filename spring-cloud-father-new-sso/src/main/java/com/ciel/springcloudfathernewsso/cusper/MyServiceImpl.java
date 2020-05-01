package com.ciel.springcloudfathernewsso.cusper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Component
public class MyServiceImpl implements MyService {

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

		Object obj = authentication.getPrincipal();

		if (obj instanceof UserDetails) {
			UserDetails user = (UserDetails) obj;
			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
			return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
		}
		return false;
	}

}
