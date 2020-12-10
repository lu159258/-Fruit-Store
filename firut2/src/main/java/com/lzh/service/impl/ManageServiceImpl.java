package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.ManageMapper;
import com.lzh.pojo.Manage;
import com.lzh.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl extends BaseServiceImpl<Manage> implements ManageService {

    @Autowired
     ManageMapper manageMapper;
    @Override
    public BaseDao<Manage> getBaseDao() {
        return manageMapper;
    }
}
