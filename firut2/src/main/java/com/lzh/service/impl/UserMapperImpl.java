package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.UserMapper;
import com.lzh.pojo.User;
import com.lzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}
