package com.ciel.springcloudfathernewsso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class HomeController {

    @PostMapping("/login")
    public Map login(HttpServletRequest request, HttpServletResponse response){

        return Map.of("name","login");
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('admin') and hasPermission('/','sys_add')")
    public Map index(){
        return Map.of("name","xiapeixin");
    }

    @GetMapping("/say")
    @PreAuthorize("hasAnyAuthority('sys_add' , 'sys_del')")
    public Map say(){
        return Map.of("name","say");
    }

    @GetMapping("/oth")
    @PreAuthorize("hasPermission('sys_add' , 'sys_del')")  //自定义认证
    public Map oth(){
        return Map.of("name","oth");
    }

    //SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
}
