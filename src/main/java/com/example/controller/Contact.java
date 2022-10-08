package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pojo.ContactList;
import com.example.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

@WebServlet(name = "Contact", value = "/Contact")
public class Contact extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符编码，防止中文乱码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        //以json数据完成操作
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(request.getContentType());// 得到客户端发送过来内容的类型，application/json;charset=UTF-8
        System.out.println(request.getRemoteAddr());// 得到客户端的ip地址，
        BufferedReader br = new BufferedReader(new InputStreamReader(// 使用字符流读取客户端发过来的数据
                request.getInputStream()));
        String line = null;
        StringBuffer s = new StringBuffer();//StringBuffer String的区别，如果要对数据作頻繁的修改，則用StringBuffer
        // 以一行的形式读取数据
        while ((line = br.readLine()) != null) {
            s.append(line);
        }
        // 关闭io流
        br.close();
        System.out.println(s.toString());// {"password":"123456","name":"admin"}
        //JSON：这是json解析包，IDEA是没有，要我们自己导入
        ContactList contactList = JSON.parseObject(s.toString(), ContactList.class);//是用了反射机制來完成对象的封闭
        //以utf-8解码操作
        String number = URLDecoder.decode(contactList.getNumber(), "utf-8");
        System.out.println(contactList);
        // 去数据库完成用户登录功能
        UserServiceImpl us = new UserServiceImpl();
        //调用登录的方法
        ContactList contactList1 = us.contact(number);
        if(contactList1  != null) {
            //将结果返回给客户端	，將結果構建成json數據返回給客戶端
            JSONObject rjson = new JSONObject();
            rjson.put("json", contactList1 );
            response.getOutputStream().write(
                    rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
        }
    }
}
