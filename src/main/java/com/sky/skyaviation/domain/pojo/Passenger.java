package com.sky.skyaviation.domain.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Passenger {
    private Integer passengerId;
    private String name;
    private String idCard;
    private String phone;
    private String email;
    private LocalDateTime createTime;
}
