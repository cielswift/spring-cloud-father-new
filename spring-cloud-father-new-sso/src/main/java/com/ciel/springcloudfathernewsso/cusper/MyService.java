package com.ciel.springcloudfathernewsso.cusper;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface MyService {
	/**
	 * 自定义的判断用户是否具有访问当前url的权限
	 * @param request
	 * @param authentication
	 * @return
	 */
	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}

