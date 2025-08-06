package com.sky.skyaviation.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.skyaviation.Mapper.OrderMapper;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
}
