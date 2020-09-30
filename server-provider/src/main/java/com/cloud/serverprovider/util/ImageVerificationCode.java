package com.cloud.serverprovider.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageVerificationCode {
    private int weight = 100;           //验证码图片的长和宽
    private int height = 40;
    private String text;                //用来保存验证码的文本内容
    private Random r = new Random();    //获取随机数对象
    //private String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};   //字体数组
    //字体数组
    private String[] fontNames = {"Georgia"};
    //验证码数组
    private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /*
      * @Method randomColor
      * @Description TODO 获取随机的颜色
      * @Params
      * @Author Administrator
      * @Return java.awt.Color
      * @Date 2020/9/30 0030 下午 5:09
      */
    private Color randomColor() {
        int r = this.r.nextInt(225);  //这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int g = this.r.nextInt(225);
        int b = this.r.nextInt(225);
        return new Color(r, g, b);            //返回一个随机颜色
    }


    /*
      * @Method randomFont
      * @Description TODO 获取随机字体
      * @Params
      * @Author Administrator
      * @Return java.awt.Font
      * @Date 2020/9/30 0030 下午 5:09
      */
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);  //获取随机的字体
        String fontName = fontNames[index];
        int style = r.nextInt(4);         //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int size = r.nextInt(10) + 24;    //随机获取字体的大小
        return new Font(fontName, style, size);   //返回一个随机的字体
    }

    /*
      * @Method randomChar
      * @Description TODO 获取随机字符
      * @Params
      * @Author Administrator
      * @Return char
      * @Date 2020/9/30 0030 下午 5:09
      */
    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    /*
      * @Method drawLine
      * @Description TODO 画干扰线，验证码干扰线用来防止计算机解析图片
      * @Params  * @param image :
      * @Author Administrator
      * @Return void
      * @Date 2020/9/30 0030 下午 5:09
      */
    private void drawLine(BufferedImage image) {
        int num = r.nextInt(10); //定义干扰线的数量
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(weight);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(weight);
            int y2 = r.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /*
      * @Method createImage
      * @Description TODO 创建图片的方法
      * @Params
      * @Author Administrator
      * @Return java.awt.image.BufferedImage
      * @Date 2020/9/30 0030 下午 5:09
      */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        //设置背景色随机
        g.setColor(new Color(255, 255, r.nextInt(245) + 10));
        g.fillRect(0, 0, weight, height);
        //返回一个图片
        return image;
    }

    /*
      * @Method getImage
      * @Description TODO 获取验证码图片的方法
      * @Params
      * @Author Administrator
      * @Return java.awt.image.BufferedImage
      * @Date 2020/9/30 0030 下午 5:09
      */
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g = (Graphics2D) image.getGraphics(); //获取画笔
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++)             //画四个字符即可
        {
            String s = randomChar() + "";      //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            sb.append(s);                      //添加到StringBuilder里面
            float x = i * 1.0F * weight / 4;   //定义字符的x坐标
            g.setFont(randomFont());           //设置字体，随机
            g.setColor(randomColor());         //设置颜色，随机
            g.drawString(s, x, height - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /*
      * @Method getText
      * @Description TODO 获取验证码文本的方法
      * @Params
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 5:10
      */
    public String getText() {
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException                  //将验证码图片写出的方法
    {
        ImageIO.write(image, "JPEG", out);
    }
}
