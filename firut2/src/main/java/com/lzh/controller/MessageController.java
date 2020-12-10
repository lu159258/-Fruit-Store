package com.lzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzh.base.BaseController;
import com.lzh.pojo.Message;
import com.lzh.service.MessageService;
import com.lzh.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 留言
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;

    @RequestMapping("/findBySql")
    public String findBySql(Model model, Message message){
        String sql = "select * from message where 1=1 ";
        if(!isEmpty(message.getName())){
            sql += " and name like '%"+message.getName()+"%'";
        }
        sql += " order by id desc";

        Pager<Message> pager = messageService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pager);
        model.addAttribute("obj",message);
        return "message/message";
    }
    @RequestMapping("/delete")
    public String delete(Message message){
        messageService.deleteById(message);
        return "redirect:/message/findBySql";
    }
    /**
     * 发表留言进入
     */
    @RequestMapping("/add")
    public String add(){
        return "message/add";
    }

    /**
     * 发表留言
     */
    @RequestMapping("/exAdd")
    @ResponseBody
    public String exAdd(Message message){
        messageService.insert(message);
        JSONObject js = new JSONObject();
        js.put("message","添加成功");
        return js.toString();
    }
}
