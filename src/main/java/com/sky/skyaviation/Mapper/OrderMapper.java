package com.sky.skyaviation.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.skyaviation.domain.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
