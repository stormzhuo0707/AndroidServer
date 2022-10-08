package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

@WebServlet(name = "Reiister", value = "/Reigister")
public class Reigister extends HttpServlet {
    public static String number;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 设置中文字符编码，防止乱码*/
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        //以json数据完成操作
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(request.getContentType());// 得到客户端发送过来内容的类型，application/json;charset=UTF-8
        System.out.println(request.getRemoteAddr());// 得到客户端的ip地址，
        BufferedReader br = new BufferedReader(new InputStreamReader(// 使用字符流读取客户端发过来的数据
                request.getInputStream()));
        String line = null;
        StringBuffer s = new StringBuffer();//StringBuffer String的区别，如果要对数据作频繁的修改，則用StringBuffer
        // 以一行的形式读取数据
        while ((line = br.readLine()) != null) {
            s.append(line);
        }
        // 关闭io流
        br.close();
        System.out.println(s.toString());
        //JSON：这是json解析包，idea是没有的，要我们自己导入
        User user = JSON.parseObject(s.toString(), User.class);//是用了发射机制來完成对象的封闭
        //以utf-8解码操作
        number = URLDecoder.decode(user.getNumber(), "utf-8");
        String name = URLDecoder.decode(user.getName(), "utf-8");
        String phone = URLDecoder.decode(user.getPhone(), "utf-8");
        String password = URLDecoder.decode(user.getPassword(), "utf-8");
        System.out.println("用户名是：" + name + ", 密码;" + password);
        System.out.println(user);
        // 去数据库完成用户注册功能
        UserService us = new UserServiceImpl();
        //调用注册的方法
        int i = us.reigisterUser(number, name, phone, password);
        boolean rs = false;
        //判断是否注册成功
        if (i > 0) {
            System.out.println("注册成功");
            rs = true;
        }
        //将结果返回给客户端	，將结果构建成json数据返回給客戶端
        JSONObject rjson = new JSONObject();
        rjson.put("json", rs);
        response.getOutputStream().write(
                rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
    }
}
