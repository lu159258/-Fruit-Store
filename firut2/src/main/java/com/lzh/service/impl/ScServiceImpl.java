package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.ScMapper;
import com.lzh.pojo.Sc;
import com.lzh.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {
    @Autowired
    private ScMapper scMapper;

    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
