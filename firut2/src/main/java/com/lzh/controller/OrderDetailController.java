package com.lzh.controller;


import com.lzh.pojo.OrderDetail;
import com.lzh.service.OrderDetailService;
import com.lzh.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/ulist")
    public String uList(Model model, OrderDetail orderDetail){
        String sql = "select * from  order_detail where order_id="+orderDetail.getOrderId();
        Pager<OrderDetail> pager = orderDetailService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pager);
        model.addAttribute("obj",orderDetail);
        return "orderDetail/ulist";
    }
}
