package com.sky.skyaviation.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.skyaviation.domain.pojo.FlightInventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlightMapper extends BaseMapper<FlightInventory> {
}
