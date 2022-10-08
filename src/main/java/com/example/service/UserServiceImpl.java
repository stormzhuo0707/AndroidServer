package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.pojo.ContactList;
import com.example.pojo.User;
import com.example.pojo.WeixinList;

public class UserServiceImpl implements UserService {
    UserDao ud = new UserDaoImpl();

    @Override
    public int reigisterUser(String number, String name, String phone, String password) {
        int i = ud.insertUser(number, name, phone, password);
        return i;
    }

    @Override
    public User login(String number, String password) {
        //调用dao层完成数据查询操作
        User user = ud.findByUsername(number);
        if (user != null) {
            //比较密码
            if (password.equals(user.getPassword())) {
                //登录成功
                return user;
            }
        }
        return null;
    }

    public WeixinList informationUser(String number) {

        //调用dao层完成数据查询操作
        WeixinList information = ud.findInformation(number);

        return information;
    }

    public ContactList contact(String number) {

        //调用dao层完成数据查询操作
        ContactList contactList = ud.findContact(number);

        return contactList;
    }
}
