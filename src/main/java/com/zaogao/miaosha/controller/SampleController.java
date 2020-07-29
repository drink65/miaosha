package com.zaogao.miaosha.controller;

import com.zaogao.miaosha.domain.User;
import com.zaogao.miaosha.result.Result;
import com.zaogao.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        System.out.println("你好啊啊");
        model.addAttribute("name","zaogao");
        return  "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user =userService.getByid(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Boolean> redisGet(){

        return Result.success(true);
    }

}
