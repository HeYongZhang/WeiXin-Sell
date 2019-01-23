package com.example.sell.rqcode;

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

public class TwoDimensionCode {
    /**
     * 生成二维码(QRCode)图片
     *
     * @param content
     *            存储内容
     * @param imgPath
     *            图片路径
     * @param imgType
     *            图片类型
     * @param output
     *            输出流
     * @param size
     *            二维码尺寸
     */

    public void encoderQRCode(String content, String imgPath) {
        this.encoderQRCode(content, imgPath, "png", 7);
    }

    public void encoderQRCode(String content, OutputStream output) {
        this.encoderQRCode(content, output, "png", 7);
    }

    public void encoderQRCode(String content, String imgPath, String imgType) {
        this.encoderQRCode(content, imgPath, imgType, 7);
    }

    public void encoderQRCode(String content, OutputStream output, String imgType) {
        this.encoderQRCode(content, output, imgType, 7);
    }


    public void encoderQRCode(String content, String imgPath, String imgType, int size) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);

            File imgFile = new File(imgPath);
            if (!imgFile.exists()) {
                imgFile.mkdirs();
            }
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encoderQRCode(String content, OutputStream output, String imgType, int size) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, imgType, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码(QRCode)图片的公共方法
     *
     * @return BufferedImage
     */
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {
        BufferedImage bufImg = null;
        try {
            Qrcode qrcode = new Qrcode();
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
            qrcode.setQrcodeErrorCorrect('M');
            qrcode.setQrcodeEncodeMode('B');
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
            qrcode.setQrcodeVersion(size);
            // 获得内容的字节数组，设置编码格式
            byte[] contentBytes = content.getBytes("utf-8");
            // 图片尺寸
            int imgSize = 67 + 12 * (size - 1);
            // BufferedImage.TYPE_INT_RGB表示一个图像，该图像具有整数像素的 8 位 RGB 颜色
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);

            Graphics2D gs = bufImg.createGraphics();
            // 设置背景颜色
            gs.setBackground(Color.WHITE);
            // 画矩形
            gs.clearRect(0, 0, imgSize, imgSize);

            // 设定图像颜色 BLACK
            gs.setColor(Color.BLACK);
            // 设置偏移量，不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容> 二维码
            if (contentBytes.length > 0 && contentBytes.length < 800) {
                // calQrcode()让字符串生成二维码。
                boolean[][] codeOut = qrcode.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
            }
            gs.dispose();
            bufImg.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImg;
    }
}
