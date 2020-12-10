package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.NewsMapper;
import com.lzh.pojo.News;
import com.lzh.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public BaseDao<News> getBaseDao() {
        return newsMapper;
    }
}
