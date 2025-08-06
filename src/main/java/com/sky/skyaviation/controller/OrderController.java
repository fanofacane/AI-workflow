package com.sky.skyaviation.controller;

import cn.hutool.json.JSONUtil;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("getOrder")
    public String getOrder() {
        List<Orders> list = orderService.lambdaQuery().eq(Orders::getPassengerId, "131313131").list();
        if(!list.isEmpty()){
            return JSONUtil.toJsonStr(list);
        }
        return null;
    }
}
