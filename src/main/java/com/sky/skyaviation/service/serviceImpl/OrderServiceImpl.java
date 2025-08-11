package com.sky.skyaviation.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.skyaviation.Mapper.OrderMapper;
import com.sky.skyaviation.ReadListener.GeoPageReadListener;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void insertByFile(String fileName) {
        // 原有实现
    }

    @Override
    public void importOrdersFromExcel(MultipartFile file) throws Exception {
        // 使用EasyExcel读取Excel文件并导入数据
        try (InputStream inputStream = file.getInputStream()) {
            // 使用自定义监听器处理数据导入
            GeoPageReadListener<Orders> listener = new GeoPageReadListener<>(this::batchInsertOrders);

            EasyExcel.read(inputStream, Orders.class, listener)
                    .sheet()
                    .doRead();
        }
    }

    /**
     * 批量插入订单数据
     * @param ordersList 订单列表
     */
    private void batchInsertOrders(List<Orders> ordersList) {
        // 使用线程池异步处理批量插入
        CompletableFuture.runAsync(() -> {
            try {
                // 调用Mapper的批量插入方法
                orderMapper.insertBatchSomeColumn(ordersList);
                System.out.println("成功插入 " + ordersList.size() + " 条订单数据");
            } catch (Exception e) {
                System.err.println("批量插入订单数据失败: " + e.getMessage());
                e.printStackTrace();
            }
        }, taskExecutor);
    }
}
