package com.zaogao.miaosha.service;

import com.mysql.cj.util.StringUtils;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.zaogao.miaosha.dao.MiaoshaUserDao;
import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.exception.GlobalException;
import com.zaogao.miaosha.redis.MiaoshaUserKey;
import com.zaogao.miaosha.redis.RedisService;
import com.zaogao.miaosha.result.CodeMsg;
import com.zaogao.miaosha.util.MD5Util;
import com.zaogao.miaosha.util.UUIDUtil;
import com.zaogao.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    //看参数
    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    //public方法第一步都是要做参数校验的
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmptyOrWhitespaceOnly(token)) {
            System.out.println("token是空的!");
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            System.out.println("getByToken.user不是NULL:" + user.toString());
            addCookie(response, token, user);
        } else if (user == null) {
            System.out.println("getByToken.user是NULL:" + user.toString());
        }
        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
//        redisService.set(MiaoshaUserKey.token,token,user);
//        Cookie cookie =new Cookie(COOKIE_NAME_TOKEN,token);
//        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
//        cookie.setPath("/");
//        response.addCookie(cookie);
        System.out.println("user:" + user.toString());
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        //String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        System.out.println("Cookie里的user" + user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
