package com.szd.z000.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 */
@Slf4j
public class QRUtils {
    private static final int QRCODE_HEIGHT = 300;
    private static final int QRCODE_WIDTH = 300;
    private static final int BARCODE_HEIGHT = 50;
    private static final int BARCODE_WIDTH = 200;
    private static final String BARCODE_FORMAT = "png";

    /**
     * 获取二维码图片
     * @param msg
     * @return
     */
    public static String getQRCode(String msg) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //容错级别为H
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //白边的宽度，可取0~4
            hints.put(EncodeHintType.MARGIN, 0);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(msg, BarcodeFormat.QR_CODE, QRCODE_HEIGHT, QRCODE_WIDTH, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, BARCODE_FORMAT, outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(outputStream.toByteArray());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 条码追加文字显示
     *
     * @param pressText     追加文字
     * @param bufferedImage
     * @param file
     */
    private static void pressText(String pressText, BufferedImage bufferedImage, File file) {
        try {
            // 创建一个 Graphics 对象
            Graphics2D g2d = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
            // 设置字体
            Font font = new Font("宋体", 14, 14);
            g2d.setFont(font);
            // 获取 FontMetrics 对象
            FontMetrics metrics = g2d.getFontMetrics();
            //计算画布高度
            int height = bufferedImage.getHeight() + 20;
            for (String txt : pressText.split(";")) {
                //判断是否需要换行
                if (metrics.stringWidth(txt) >= bufferedImage.getWidth() - 50) {
                    int num = (int) Math.ceil(metrics.stringWidth(txt) / (bufferedImage.getWidth() - 50.0));
                    height += (5 + metrics.getHeight()) * num;
                } else {
                    height += 5 + metrics.getHeight();
                }
            }
            log.info("height {}", height);
            //创建画布
            BufferedImage bi = new BufferedImage(bufferedImage.getWidth(), height, BufferedImage.TYPE_INT_RGB);
            //创建画笔
            Graphics2D g = bi.createGraphics();
            g.setBackground(Color.white);
            //填充
            g.fillRect(0, 0, bufferedImage.getWidth(), height);
            //设置绘制图像 0, 0 代表在画布长宽坐标分别是0，0开始
            g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            g.setColor(Color.black);
            g.setFont(font);
            /** 防止生成的文字带有锯齿 **/
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            double baseY = bufferedImage.getHeight();
            for (String txt : pressText.split(";")) {
                //判断是否需要换行
                if (g.getFontMetrics().stringWidth(txt) >= bufferedImage.getWidth()) {
                    //设置换行次数
                    int num = (int) Math.ceil(g.getFontMetrics().stringWidth(txt) / bufferedImage.getWidth());
                    for (int i = 0; i < num; i++) {
                        baseY += 5 + metrics.getHeight();
                        int let = (int) Math.ceil(txt.length() / num);
                        int star = (let) * i;
                        int end = (let) * (i + 1);
                        String t = txt.substring(star, end);
                        double x = (bufferedImage.getWidth() - metrics.stringWidth(t)) / 2;
                        g.drawString(t, (int) x, (int) baseY);
                    }
                } else {
                    double x = (bufferedImage.getWidth() - metrics.stringWidth(txt)) / 2;
                    //设置字体内容 坐标x,y
                    baseY += 5 + metrics.getHeight();
                    g.drawString(txt, (int) x, (int) baseY);
                }
            }
            //释放对象
            g.dispose();
            ImageIO.write(bi, "png", file);
            bi.flush();
        } catch (Exception e) {
            log.info("二维码追加文字 err ", e);
        }
    }

}
