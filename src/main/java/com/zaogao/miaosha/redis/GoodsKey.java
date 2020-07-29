package com.zaogao.miaosha.redis;

import com.zaogao.miaosha.domain.Goods;

public class GoodsKey extends BasePrefix {
    private GoodsKey(int expireSecond ,String prefix){
        super(prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
}
