package com.example.dao;

import com.example.controller.Reigister;
import com.example.pojo.ContactList;
import com.example.pojo.User;
import com.example.pojo.WeixinList;
import com.example.util.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDaoImpl implements UserDao {
    ResultSet rs;

    @Override
    public int insertUser(String number, String name, String phone, String password) {
        String sql = "insert into user (number, name, phone, password, remark, headimg) values(?,?,?,?,?,?);";
        //i如果操作成功，就是操作成功的条数
        int i = JDBCUtil.executeUpdate(sql, number, name, phone, password, "1", "http://100.2.178.10:8080/" +
                "AndroidServer_war_exploded/imgs/contact/" + Reigister.number + "." + "png");
        if (i > 0) {
            String sql1 = "insert into contact (img, name, number) values(?,?,?);";
            int i1 = JDBCUtil.executeUpdate(sql1, "http://100.2.178.10:8080/" +
                    "AndroidServer_war_exploded/imgs/contact/" + Reigister.number + "." + "png", name, Reigister.number);
        }
        return i;
    }

    @Override
    public User findByUsername(String number) {
        //判断数据是用户名还是手机
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(number);
        //手机sql执行语句
        if (matcher.matches()) {
            //sql
            String sql = "select * from user where phone=?";
            rs = JDBCUtil.executeQuery(sql, number);
        } else {  //用户名sql执行语句
            //sql
            String sql = "select * from user where number=?";
            rs = JDBCUtil.executeQuery(sql, number);
        }
        //判断是否查询到用户
        try {
            if (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String number1 = rs.getString("number");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String remark = rs.getString("remark");
                //将查询到的用户封装到一个User对象中
                User user = new User();
                user.setId(id);
                user.setNumber(number1);
                user.setName(name);
                user.setPassword(password);
                user.setPhone(phone);
                user.setRemark(remark);
                System.out.println("查询到的用户" + user);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public WeixinList findInformation(String number) {
        //sql
        String sql = "select * from weixinlist where number=?;";
        ResultSet rs = JDBCUtil.executeQuery(sql, number);
        //判断是否查询到用户
        try {
            if (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String titleimg = rs.getString("titleimg");
                String title1 = rs.getString("title");
                String content = rs.getString("content");
                String time = rs.getString("time");
                String showcode = rs.getString("showcode");
                String number1 = rs.getString("number");
                //将查询到的用户封装到一个User对象中
                WeixinList weixinList = new WeixinList();
                weixinList .setId(id);
                weixinList .setTitleimg(titleimg);
                weixinList .setTitle(title1);
                weixinList .setContent(content);
                weixinList .setTime(time);
                weixinList .setShowcode(showcode);
                weixinList .setNumber(number1);
                System.out.println("查询到的用户" + weixinList);
                return weixinList;
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ContactList findContact(String number) {

        String sql = "select * from contact where number=?;";
        ResultSet rs = JDBCUtil.executeQuery(sql, number);
        //判断是否查询到用户
        try {
            if (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String img = rs.getString("img");
                String name = rs.getString("name");
                String number1 = rs.getString("number");
                //将查询到的用户封装到一个User对象中
                ContactList contactList = new ContactList();
                contactList .setId(id);
                contactList .setImg(img);
                contactList .setName(name);
                contactList .setNumber(number1);
                System.out.println("查询到的用户" + contactList);
                return contactList;
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
