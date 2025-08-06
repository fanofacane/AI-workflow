package com.sky.skyaviation.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Orders {
    private Integer orderId;
    private Integer passengerId;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String planeName;
    private String cabinType;
    private BigDecimal price;
    private LocalDateTime orderTime;
}
