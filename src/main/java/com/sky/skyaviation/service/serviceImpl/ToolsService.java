package com.sky.skyaviation.service.serviceImpl;

import cn.hutool.json.JSONUtil;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.FlightService;
import com.sky.skyaviation.service.OrderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ToolsService {
    @Autowired
    private OrderService orderService;
    @Tool(description = "查询用户订单信息，查询前需要让用户提供用户ID")
    String getOrderByid(@ToolParam(description = "用户ID,可以是纯数字") String id) {
        List<Orders> list = orderService.lambdaQuery().eq(Orders::getPassengerId, "131313131").list();
        if(!list.isEmpty()){
            return JSONUtil.toJsonStr(list);
        }
        return null;
    }

}
