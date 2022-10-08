package com.example.controller;

import com.example.util.ChangeImage;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@WebServlet(name = "Image", value = "/Image")
public class Image extends HttpServlet {
    public static ImageIcon ii; //图片猴嘴
    byte[] imageByte;
    public static String headUrl; //图片绝对路径
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符编码，防止中文乱码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(request.getContentType());// 得到客户端发送过来内容的类型，application/json;charset=UTF-8
        System.out.println(request.getRemoteAddr());// 得到客户端的ip地址，

        //返回套接字具有关联的通道输入流，则得到的输入流会将其所有操作委托给通道
        InputStream in=request.getInputStream();

        //将输入流按照下面方式处理， 根据Iterator<ImageReader> itImage是否能
        //成功的返回一个ImageReader对象确认该流文件是否是一个图片文件！
        //并ImageReader类中的getFormatName（）得到文件的格式！
        //通过最后可以通过ImageIcon的byte[]构造函数建立ImageIcon对象！
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        Iterator<ImageReader> itImage = ImageIO.getImageReaders(iis);
        if(itImage.hasNext()){
            ImageReader reader = itImage.next();
            imageByte = new byte[102400*10];
            iis.read(imageByte);
            ii = new ImageIcon(imageByte, reader.getFormatName());
        }
        //存储路径
        headUrl = "C:\\Users\\Administrator\\IdeaProjects\\" +
                "AndroidServer\\src\\main\\webapp\\imgs\\contact\\" + Reigister.number  + "." + ii;
        FileOutputStream fos=new FileOutputStream(headUrl);
        fos.write(imageByte); //写文件
        fos.close();
        //创建改变图片大小的工具类对象
        ChangeImage changeImage = new ChangeImage();
        try{

            BufferedImage originalImage = ImageIO.read(new File(headUrl));
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = changeImage.resizeImage(originalImage, type);
            ImageIO.write(resizeImageJpg, "jpg", new File(headUrl));

            BufferedImage resizeImagePng = changeImage.resizeImage(originalImage, type);
            ImageIO.write(resizeImagePng, "png", new File(headUrl));

            BufferedImage resizeImageHintJpg = changeImage.resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImageHintJpg, "jpg", new File(headUrl));

            BufferedImage resizeImageHintPng = changeImage.resizeImageWithHint(originalImage, type);
            ImageIO.write(resizeImageHintPng, "png", new File(headUrl));

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
