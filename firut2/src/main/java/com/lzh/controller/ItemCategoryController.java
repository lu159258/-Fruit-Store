package com.lzh.controller;

import com.lzh.base.BaseController;

import com.lzh.pojo.ItemCategory;
import com.lzh.service.ItemCategoryService;

import com.lzh.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 类目
 */

@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping("/findBySql")
    public String findBySql(Model model, ItemCategory itemCategory) {
        String sql = "select * from item_category where isDelete = 0 and pid is null order by id";
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        System.out.println(pagers);
        model.addAttribute("pagers", pagers);
        model.addAttribute("obj", itemCategory);
        return "itemCategory/itemCategory";
    }

    @RequestMapping("/add")
    public String add() {
        return "itemCategory/add";
    }

    /**
     * 新增一级分类保存
     */
    @RequestMapping("/exAdd")
    public String exAdd(ItemCategory itemCategory) {
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql.action";
    }

    @RequestMapping("/update")
    public String update(int id, Model model) {
        ItemCategory obj = itemCategoryService.load(id);
        model.addAttribute("obj", obj);
        return "itemCategory/update";
    }

    @RequestMapping("/exUpdate")
    public String exUpdate(ItemCategory itemCategory) {
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql.action";

    }

    @RequestMapping("/delete")
    public String delete(int id) {
        ItemCategory load = itemCategoryService.load(id);
        load.setIsDelete(1);

        itemCategoryService.updateById(load);
        String sql = "update item_category set isDelete=1 where pid=" + id;
        itemCategoryService.updateBysql(sql);
        return "redirect:/itemCategory/findBySql.action";
    }

    /**
     * 查看二级分类
     */

    @RequestMapping("/findBySql2")
    public String findBySql2(ItemCategory itemCategory, Model model) {
        String sql = "select * from item_category where isDelete=0 and pid=" + itemCategory.getPid() + " order by id";
        Pager<ItemCategory> pager = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers", pager);
        model.addAttribute("obj", itemCategory);
        return "itemCategory/itemCategory2";
    }

    @RequestMapping("/add2")
    public String add2(int pid, Model model) {
        model.addAttribute("pid", pid);
        return "itemCategory/add2";
    }

    /**
     * 新增二级类目保存功能
     */
    @RequestMapping("/exAdd2")
    public String exAdd2(ItemCategory itemCategory) {
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql2.action?pid=" + itemCategory.getPid();
    }

    @RequestMapping("/update2")
    public String update2(int id, Model model) {
        ItemCategory obj = itemCategoryService.load(id);
        model.addAttribute("obj", obj);
        return "itemCategory/update2";
    }

    @RequestMapping("/exUpdate2")
    public String exUpdate2(ItemCategory itemCategory) {
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql2.action?pid=" + itemCategory.getPid();
    }

    @RequestMapping("/delete2")
    public String delete2(int id, int pid) {
        ItemCategory load = itemCategoryService.load(id);
        load.setIsDelete(1);

        itemCategoryService.updateById(load);
        return "redirect:/itemCategory/findBySql2.action?pid=" + pid;
    }



}
