package com.sky.skyaviation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.skyaviation.domain.pojo.Orders;
import org.springframework.web.multipart.MultipartFile;

public interface OrderService extends IService<Orders> {
    /**
     * 从文件导入数据
     * @param fileName 文件名
     */
    void insertByFile(String fileName);

    /**
     * 从上传的Excel文件导入订单数据
     * @param file 上传的Excel文件
     */
    void importOrdersFromExcel(MultipartFile file) throws Exception;
}
