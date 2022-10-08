package com.example.service;

import com.example.pojo.ContactList;
import com.example.pojo.User;
import com.example.pojo.WeixinList;

public interface UserService {
    //注册用户
    int reigisterUser(String number, String name, String phone, String password);

    //用户登录
    User login(String number,String password);

    //微信消息列表
    WeixinList informationUser(String number);

    //微信通讯录
    ContactList contact(String number);
}
