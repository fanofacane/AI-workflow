package com.sky.skyaviation.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.skyaviation.Mapper.FlightMapper;
import com.sky.skyaviation.domain.pojo.FlightInventory;
import com.sky.skyaviation.service.FlightService;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, FlightInventory> implements FlightService {

}
