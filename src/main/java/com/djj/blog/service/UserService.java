package com.djj.blog.service;

import com.djj.blog.dao.UserDao;
import com.djj.blog.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public boolean login(String username, String password){
        User user = userDao.findByUsernameAndPassword(username, password);
        if(user==null){
            return false;
        }else{
            return true;
        }
    }

    public User fingByUsername(String username){
        User user = userDao.findByUsername(username);
        return user;
    }

}
