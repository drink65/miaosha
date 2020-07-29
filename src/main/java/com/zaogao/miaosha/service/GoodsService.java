package com.zaogao.miaosha.service;

import com.zaogao.miaosha.dao.GoodsDao;
import com.zaogao.miaosha.domain.Goods;
import com.zaogao.miaosha.domain.MiaoshaGoods;
import com.zaogao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return  goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void  reduceStock(GoodsVo goodsVo){
        MiaoshaGoods miaoshaGoods =new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(miaoshaGoods);
    }
}
