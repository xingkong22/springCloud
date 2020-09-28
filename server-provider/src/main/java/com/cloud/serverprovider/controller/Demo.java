package com.cloud.serverprovider.controller;

import com.cloud.serverprovider.pojo.MyPojo;
import com.cloud.serverprovider.util.ImageVerificationCode;
import com.cloud.serverprovider.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/provider")
//@EnableConfigurationProperties({MyPojo.class})
public class Demo {

    public static final String UPLOAD_PATH = "F://MyQRCode";

    @Value("${showImgPrefixPath}")
    private String showImgPrefixPath;

    @Value("${imgPath}")
    private String imgPath;

//    @Autowired
//    private MyPojo myPojo;

    @RequestMapping("/msg01")
    public String msg() {
        return "我是server-provider, Demo():msg01";
    }


    @GetMapping(value = "/code")
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImageVerificationCode ivc = new ImageVerificationCode();     //用我们的验证码类，生成验证码类对象
        BufferedImage image = ivc.getImage();  //获取验证码
        Cookie cookie = new Cookie("text", ivc.getText());
        cookie.setMaxAge(60 * 5);
        cookie.setPath("/");
        response.addCookie(cookie);
        //request.getSession().setAttribute("text", ivc.getText()); //将验证码的文本存在session中
        ivc.output(image, response.getOutputStream());//将验证码图片响应给客户端
    }


    @GetMapping(value = "/createQRCode")
    @ResponseBody
    public void createQRCode() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "daixing");
        map.put("age", 22);
        map.put("addre", "河南新乡");
        map.put("tel", "18365478956");

        QRCodeGenerator.generateQRCodeImage(new String(map.toString().getBytes("UTF-8"), "ISO-8859-1"), 350, 350, QRCodeGenerator.QR_CODE_IMAGE_PATH);
    }


    @PostMapping("/uploadParseEWMa")
    public Map<String, Object> uploadParseEWMa(HttpServletRequest request, @RequestParam(value = "fileEWMa", required = false) MultipartFile multipartFile)
            throws IllegalStateException, IOException {
        Map<String, Object> map = new HashMap<>();
        if (multipartFile.isEmpty()) {
            map.put("succ", false);
            map.put("msg", "文件为空");
            return map;
        }
        String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        File dest = new File(new File(UPLOAD_PATH).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        multipartFile.transferTo(dest); // 保存文件

        Map<String, Object> mapData = QRCodeGenerator.readQRCodeImageToFile(dest);
        map.putAll(mapData);
        map.put("succ", true);
        map.put("msg", "成功");
        map.put("path", showImgPrefixPath + fileName);
        return map;
    }


    @PostMapping("/ceshiForm")
    public Map<String, Object> ceshiForm(@RequestParam Map map)
            throws IOException {
        String basename = java.net.URLDecoder.decode(map.get("basename").toString(), "UTF-8");
        String nsjh = java.net.URLDecoder.decode(map.get("nsjh").toString(), "UTF-8");

        map.put("basename", basename);
        map.put("nsjh", nsjh);
        map.put("succ", true);
        map.put("msg", "成功");
        return map;
    }


    @RequestMapping("/getPic")
    public void getPic(MultipartRequest request) {
        try {
            MultipartFile file = request.getFile("file");
            String fileRealName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String imgName = uuid + "-" + fileRealName;
            String loadUrl = this.imgPath + "/" + imgName;
            File file1 = new File(loadUrl);
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            file1.mkdir();
            file.transferTo(file1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
