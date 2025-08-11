package com.sky.skyaviation.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class BarcodeGenerator {
    /**
     * 生成条形码图片
     *
     * @param content  要编码的内容
     * @param width    图片宽度
     * @param height   图片高度
     * @param filePath 保存路径
     * @throws WriterException 编码异常
     * @throws IOException     IO异常
     */
    public static void generateBarcode(String content, int width, int height, String filePath)
            throws WriterException, IOException {
        // 创建Code128条形码写入器
        Code128Writer writer = new Code128Writer();

        // 生成位矩阵
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, width, height);

        // 将位矩阵转换为图片并保存
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
    }

    /**
     * 生成条形码图片并写入输出流
     *
     * @param content      要编码的内容
     * @param width        图片宽度
     * @param height       图片高度
     * @param outputStream 输出流
     * @throws WriterException 编码异常
     * @throws IOException     IO异常
     */
    public static void generateBarcode(String content, int width, int height, OutputStream outputStream)
            throws WriterException, IOException {
        Code128Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, width, height);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
    }

    /**
     * 生成条形码图片对象
     *
     * @param content 要编码的内容
     * @param width   图片宽度
     * @param height  图片高度
     * @return 条形码图片对象
     * @throws WriterException 编码异常
     */
    public static BufferedImage generateBarcodeImage(String content, int width, int height)
            throws WriterException {
        Code128Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
