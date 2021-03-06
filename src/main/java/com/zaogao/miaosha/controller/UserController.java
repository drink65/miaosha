package com.zaogao.miaosha.controller;

import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.redis.RedisService;
import com.zaogao.miaosha.result.Result;
import com.zaogao.miaosha.service.GoodsService;
import com.zaogao.miaosha.service.MiaoshaUserService;
import com.zaogao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
        return Result.success(user);
    }

}
