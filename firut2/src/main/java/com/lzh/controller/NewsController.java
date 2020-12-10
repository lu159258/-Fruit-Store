package com.lzh.controller;


import com.lzh.base.BaseController;
import com.lzh.pojo.News;
import com.lzh.service.NewsService;
import com.lzh.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 公告
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;
    @RequestMapping("/findBySql")
    public String findBySql(Model model, News news){
        String sql = "select * from news where 1=1 ";
        if(!isEmpty(news.getName())){
            sql += " and name like '%"+news.getName()+"%'";
        }
        sql += " order by id desc";
        Pager<News> pager = newsService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pager);
        model.addAttribute("obj",news);
        return "news/news";

    }

    @RequestMapping("/add")
    public String add(){
        return "news/add";
    }

    @RequestMapping("/exAdd")
    public String exAdd(News news){
        news.setAddTime(new Date());
        newsService.insert(news);
        return "redirect:/news/findBySql";

    }

    @RequestMapping("/update")
    public String update(int id,Model model) {
        News load = newsService.load(id);
        model.addAttribute("obj",load);
        return "news/update";

    }

    @RequestMapping("/exUpdate")
    public String exUpdate(News news){
        newsService.updateById(news);
        return "redirect:/news/findBySql";
    }

    @RequestMapping("/delete")
    public String delete(News news){
        newsService.deleteById(news);
        return "redirect:/news/findBySql";
    }
    /**
     * 前端公告列表
     */
    @RequestMapping("/list")
    public String list(Model model){
        Pager<News> pager = newsService.findByEntity(new News());
        model.addAttribute("pagers",pager);
        return  "news/list";
    }
    /**
     * 公告详情页面
     */
    @RequestMapping("/view")
    public String view(Model model,int id){
        News load = newsService.load(id);
        model.addAttribute("obj",load);
        return "news/view";
    }
}
