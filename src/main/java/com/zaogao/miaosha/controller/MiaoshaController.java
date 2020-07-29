package com.zaogao.miaosha.controller;

import com.zaogao.miaosha.domain.MiaoshaOrder;
import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.domain.OrderInfo;
import com.zaogao.miaosha.redis.RedisService;
import com.zaogao.miaosha.result.CodeMsg;
import com.zaogao.miaosha.result.Result;
import com.zaogao.miaosha.service.GoodsService;
import com.zaogao.miaosha.service.MiaoshaService;
import com.zaogao.miaosha.service.MiaoshaUserService;
import com.zaogao.miaosha.service.OrderService;
import com.zaogao.miaosha.vo.GoodsVo;
import com.zaogao.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;


    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        //商品库存判断
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stoke = goodsVo.getStockCount();
        if (stoke <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否秒杀成功
        MiaoshaOrder order =orderService.getMiashaOrderByUserId(user.getId(),goodsId);
        if (order!=null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo =miaoshaService.miaosha(user,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "order_detail";

    }
}
