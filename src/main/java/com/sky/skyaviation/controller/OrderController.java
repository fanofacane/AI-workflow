package com.sky.skyaviation.controller;

import cn.hutool.json.JSONUtil;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("insertByFile")
    public String insertByFile(String fileName) {
        orderService.insertByFile(fileName);
        return "success";
    }

    /**
     * 上传Excel文件并批量导入数据
     * @param file 上传的Excel文件
     * @return 导入结果
     */
    @PostMapping("uploadAndImport")
    public String uploadAndImport(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 调用服务层处理文件导入
            orderService.importOrdersFromExcel(file);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败: " + e.getMessage();
        }
    }
}
