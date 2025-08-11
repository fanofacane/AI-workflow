package com.sky.skyaviation.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.skyaviation.domain.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    @Select("SELECT * FROM orders WHERE order_id > (" +
            "SELECT order_id FROM orders LIMIT 270000, 1" +
            ") LIMIT 20")
    List<Orders> selectOrdersWithSubQuery();

    @Select("SELECT * FROM orders LIMIT #{start},#{nums}")
    List<Orders> selectOrders(Integer start, Integer nums);

    int insertBatchSomeColumn(List<Orders> OrderList);
}
