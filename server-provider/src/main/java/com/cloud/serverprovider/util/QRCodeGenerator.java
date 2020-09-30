package com.cloud.serverprovider.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static final String QR_CODE_IMAGE_PATH = "F://MyQRCode.png";

    /*
      * @Method generateQRCodeImage
      * @Description TODO 生成二维码
      * @Params  * @param text :
     * @param width :
     * @param height :
     * @param filePath :
      * @Author Administrator
      * @Return void
      * @Date 2020/9/30 0030 下午 5:08
      */
    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
    }


    public static void readQRCodeImage(String filePath) {
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(filePath);

        try {
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            //定义二维码的参数
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("二维码解析结果：" + result.toString());
            System.out.println("二维码的格式：" + result.getBarcodeFormat());
            System.out.println("二维码的文本内容：" + result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<String, Object> readQRCodeImageToFile(File file) {
        Map<String, Object> map = new HashMap<>();
        MultiFormatReader formatReader = new MultiFormatReader();
        try {
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            //定义二维码的参数
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            map.put("result", result.toString());
            map.put("type", result.getBarcodeFormat());
            map.put("content", result.getText());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "daixing");
            map.put("age", 22);
            map.put("addre", "河南新乡");
            map.put("tel", "18365478956");
            //new String(map.toString().getBytes("UTF-8"), "ISO-8859-1")，不设置的话，读取的时候，中文会乱码
            //generateQRCodeImage(new String(map.toString().getBytes("UTF-8"), "ISO-8859-1"), 350, 350, QR_CODE_IMAGE_PATH);

            readQRCodeImage(QR_CODE_IMAGE_PATH);
        } catch (Exception e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }
}
