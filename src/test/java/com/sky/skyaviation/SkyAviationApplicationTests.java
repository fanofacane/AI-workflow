package com.sky.skyaviation;

import com.alibaba.excel.EasyExcel;
import com.sky.skyaviation.Mapper.OrderMapper;
import com.sky.skyaviation.domain.pojo.Orders;
import com.sky.skyaviation.service.OrderService;
import com.sky.skyaviation.utils.BarcodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
class SkyAviationApplicationTests {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Test
    void contextLoads() {
        Orders orders = new Orders();
        orders.setPassengerId("1222222222");
        orders.setFlightNumber("CZ1234");
        orders.setDepartureCity("上海");
        orders.setArrivalCity("北京");
        orders.setDepartureTime(LocalDateTime.now());
        orders.setArrivalTime(LocalDateTime.now());
        orders.setPlaneName("CZ1234");
        orders.setCabinType("头等舱");
        orders.setPrice(new BigDecimal("1000"));
        orders.setOrderTime(LocalDateTime.now());

        try {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {
                orderService.save(orders);

                // 每100条记录显示进度
                if (i % 1000 == 0) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("进度: " + (i + 1) + "/100000, 耗时: " + elapsedTime + "ms");
                }

                // 每5000条记录暂停一下，避免连接池耗尽
                if (i % 5000 == 0 && i > 0) {
                    Thread.sleep(1000); // 暂停1秒
                }
            }

            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("插入成功，总耗时: " + totalTime + "ms");

        } catch (Exception e) {
            System.err.println("插入失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void context() {
// 记录开始时间
        long startTime = System.currentTimeMillis();

// 执行 MyBatis-Plus 查询操作
        List<Orders> orders = orderMapper.selectOrdersWithSubQuery();

// 记录结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("查询结果:"+orders.size());
        System.out.println("查询耗时：" + costTime + "毫秒");
    }
    @Test
    void context1() {
        // 记录开始时间
        long startTime = System.currentTimeMillis();

// 执行 MyBatis-Plus 查询操作
//        List<Orders> orders = orderMapper.selectOrders();

// 记录结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
//        System.out.println("查询结果:"+orders.size());
        System.out.println("查询耗时：" + costTime + "毫秒");
    }
    @Test
    void context2() throws Exception {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        Integer nums = 5000; // 每个线程查询5000条数据
        int totalThreads = 4; // 200000/5000=4个线程

        // 创建CompletableFuture列表来存储所有异步任务
        List<CompletableFuture<List<Orders>>> futures = new ArrayList<>();

        // 提交4个异步任务，每个任务查询5000条数据
        for (int i = 0; i < totalThreads; i++) {
            final int pageIndex = i;
            CompletableFuture<List<Orders>> future = CompletableFuture.supplyAsync(() -> {
                // 调用mapper方法分页查询数据
                return orderMapper.selectOrders(pageIndex * nums + 1, nums);
            });
            futures.add(future);
        }

        // 使用allOf等待所有任务完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );
        // 等待所有任务完成
        allFutures.join();

        // 收集所有结果
        List<Orders> orders = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        // 导出合并后的数据到Excel
        String fileName = "export.xlsx";
        EasyExcel.write(fileName, Orders.class).sheet("Sheet1").doWrite(orders);
// 记录结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("查询耗时：" + costTime + "毫秒");
        System.out.println("总共导出数据条数：" + orders.size());
    }
    @Test
    void context3() {
        try {
            String content = "112314"; // 要生成条形码的内容
            int width = 300; // 条形码宽度
            int height = 100; // 条形码高度
            String filePath = "D:/software/rubbish/picture/barcode.png"; // 保存路径

            BarcodeGenerator.generateBarcode(content, width, height, filePath);
            System.out.println("条形码生成成功，保存路径：" + new File(filePath).getAbsolutePath());
        } catch (Exception e) {
            System.out.println("生成条形码失败：" + e.getMessage());
            e.printStackTrace();
        }
    }


}
