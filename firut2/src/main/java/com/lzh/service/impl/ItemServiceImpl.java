package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.ItemMapper;
import com.lzh.pojo.Item;
import com.lzh.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public BaseDao<Item> getBaseDao() {
        return itemMapper;
    }
}
