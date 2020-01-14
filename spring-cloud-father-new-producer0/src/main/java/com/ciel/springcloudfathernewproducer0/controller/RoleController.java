package com.ciel.springcloudfathernewproducer0.controller;

import com.ciel.api.IRoleService;
import com.ciel.entity.Role;
import com.ciel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    protected IRoleService roleService;

    @GetMapping("/rs")
    public List<Role> rs(){
        Role role = new Role();
        role.setName("耶和华");
        List<Role> list = roleService.list();
        list.add(role);
        return list;
    }


    @GetMapping("/roles")
    public List<Role> roles(User user){
        System.out.println(user);

        Role role = new Role();
        role.setName("耶和华");
        List<Role> list = roleService.list();
        list.add(role);
        return list;
    }

    @PostMapping("/roles")
    public List<Role> rolePost(@RequestBody User user){

        System.out.println(user);

        Role role = new Role();
        role.setName("耶和华");
        List<Role> list = roleService.list();
        list.add(role);
        return list;
    }


    @PostMapping("/file")
    public Map upload(@RequestParam("file") MultipartFile file) throws IOException {
        file.transferTo(new File("c:/ciel/zzing/zi.jpg"));

        return Map.of("url","c:/ciel/zzing/zi.jpg");
    }
}
