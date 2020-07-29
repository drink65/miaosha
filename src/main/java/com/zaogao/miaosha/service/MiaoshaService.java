package com.zaogao.miaosha.service;

import com.zaogao.miaosha.dao.GoodsDao;
import com.zaogao.miaosha.domain.Goods;
import com.zaogao.miaosha.domain.MiaoshaUser;
import com.zaogao.miaosha.domain.OrderInfo;
import com.zaogao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goodsVo);
        //order_info表 miaosha_order
        return orderService.createOrder(user,goodsVo);


    }
}
