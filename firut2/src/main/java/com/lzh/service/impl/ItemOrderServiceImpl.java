package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.ItemMapper;
import com.lzh.mapper.ItemOrderMapper;
import com.lzh.pojo.ItemOrder;
import com.lzh.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderServiceImpl extends BaseServiceImpl<ItemOrder> implements ItemOrderService {
   @Autowired
   private ItemOrderMapper itemOrderMapper;

    @Override
    public BaseDao<ItemOrder> getBaseDao() {
        return itemOrderMapper;
    }
}
