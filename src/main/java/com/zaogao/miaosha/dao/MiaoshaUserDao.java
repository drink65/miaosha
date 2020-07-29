package com.zaogao.miaosha.dao;

import com.zaogao.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id =#{id}")
    public MiaoshaUser getById(@Param("id") long id);
}
