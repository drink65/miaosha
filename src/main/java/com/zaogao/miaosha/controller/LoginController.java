package com.zaogao.miaosha.controller;

import com.mysql.cj.util.StringUtils;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.domain.User;
import com.zaogao.miaosha.result.CodeMsg;
import com.zaogao.miaosha.result.Result;
import com.zaogao.miaosha.service.MiaoshaUserService;
import com.zaogao.miaosha.service.UserService;
import com.zaogao.miaosha.util.ValidatorUtil;
import com.zaogao.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/to_login")
 //   @ResponseBody  这个注释是在使用此注解之后不会再走视图处理器，
    //   而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
    public String toLogin(Model model){
        model.addAttribute("name","zaogao");
        return  "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> dologin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());

        //登陆
        //miaoshaUserService.login(response,loginVo);
        String token =miaoshaUserService.login(response,loginVo);
        return Result.success(token);
    }
}
