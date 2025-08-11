package com.sky.skyaviation.domain.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@HeadRowHeight(30)  //表头行高
@ContentRowHeight(15)  //内容行高
@ColumnWidth(18)  //列宽
@ContentFontStyle(fontHeightInPoints = (short) 12) //字体大小
public class Orders {
    @ExcelProperty("订单编号")
    private Integer orderId;
    @ExcelProperty("乘客编号")
    private String passengerId;
    @ExcelProperty("航班编号")
    private String flightNumber;
    @ExcelProperty("出发城市")
    private String departureCity;
    @ExcelProperty("到达城市")
    private String arrivalCity;
    @ExcelProperty("出发时间")
    private LocalDateTime departureTime;
    @ExcelProperty("到达时间")
    private LocalDateTime arrivalTime;
    @ExcelProperty("飞机编号")
    private String planeName;
    @ExcelProperty("座位等级")
    private String cabinType;
    @ExcelProperty("价格")
    private BigDecimal price;
    @ExcelProperty("下单时间")
    private LocalDateTime orderTime;
}
