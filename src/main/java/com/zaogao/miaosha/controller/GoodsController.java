package com.zaogao.miaosha.controller;

import com.mysql.cj.util.StringUtils;
import com.zaogao.miaosha.domain.Goods;
import com.zaogao.miaosha.domain.IWebContextImpl;
import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.domain.User;
import com.zaogao.miaosha.redis.GoodsKey;
import com.zaogao.miaosha.redis.MiaoshaUserKey;
import com.zaogao.miaosha.redis.RedisService;
import com.zaogao.miaosha.result.Result;
import com.zaogao.miaosha.service.GoodsService;
import com.zaogao.miaosha.service.MiaoshaUserService;
import com.zaogao.miaosha.vo.GoodsDetailVo;
import com.zaogao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;


    //
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String tolist(HttpServletResponse response, HttpServletRequest request, Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        //     return "goods_list";
        //取出缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmptyOrWhitespaceOnly(html)) {
            return html;
        }
        IWebContext iWebContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", iWebContext);
        if (!StringUtils.isEmptyOrWhitespaceOnly(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }


    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;


        if (now < startAt) {//秒杀没开始
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

    @RequestMapping("/to_detail2/{goodsId}")
    public String detail2(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;


        if (now < startAt) {//秒杀没开始
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }


}
