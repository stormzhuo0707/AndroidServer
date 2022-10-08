package com.example.dao;

import com.example.pojo.ContactList;
import com.example.pojo.User;
import com.example.pojo.WeixinList;

public interface UserDao {
    //添加用户
    int insertUser(String number, String name, String phone, String password);

    //查询用户通过微信号
    User findByUsername(String number);

    //查询微信消息列表
    WeixinList findInformation(String number);

    //查询微信通信录列表
    ContactList findContact(String number);
}
