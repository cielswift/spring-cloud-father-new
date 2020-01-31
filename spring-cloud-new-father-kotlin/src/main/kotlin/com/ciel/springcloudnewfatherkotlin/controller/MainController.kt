package com.ciel.springcloudnewfatherkotlin.controller

import com.ciel.springcloudnewfatherkotlin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class MainController {

    @Autowired
    lateinit var user : UserService;

    @GetMapping("/main")
    fun main(): Any {
        return user.userInfo()
    }



}