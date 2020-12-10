package com.lzh.service.impl;

import com.lzh.base.BaseDao;
import com.lzh.base.BaseServiceImpl;
import com.lzh.mapper.OrderDetailMapper;
import com.lzh.pojo.OrderDetail;
import com.lzh.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Override
    public BaseDao<OrderDetail> getBaseDao() {
        return orderDetailMapper;
    }
}
