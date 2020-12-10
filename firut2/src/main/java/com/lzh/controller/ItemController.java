package com.lzh.controller;


import com.lzh.base.BaseController;
import com.lzh.pojo.Item;
import com.lzh.pojo.ItemCategory;
import com.lzh.service.ItemCategoryService;
import com.lzh.service.ItemService;
import com.lzh.utils.Pager;
import com.lzh.utils.SystemContext;
import com.lzh.utils.UUIDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    @RequestMapping("/findBySql")
    public String findBySql(Model model,Item item){
        String sql = "select * from item where isDelete = 0 ";
        if(!isEmpty(item.getName())){
            sql += " and name like '%" + item.getName() + "%' ";
        }
        sql += " order by id desc";
        Pager<Item> pager = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pager);
        model.addAttribute("obj",item);
        return "item/item";
    }

    @RequestMapping("/add")
    public String add(Model model){
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> itemCategories = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",itemCategories);
        return "item/add";
    }

    /**
     * 执行添加商品
     */
    @RequestMapping("/exAdd")
    public String exAdd(Item item, HttpServletRequest request, @RequestParam("file")CommonsMultipartFile[] files) throws IOException {
       itemCommon(item, request, files);
        item.setGmNum(0);
        item.setIsDelete(0);
        item.setScNum(0);
        itemService.insert(item);
       return  "redirect:/item/findBySql.action";
    }

    @RequestMapping("/update")
    public String update(int id,Model model){
        Item obj = itemService.load(id);
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> itemCategories = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",itemCategories);
        model.addAttribute("obj",obj);
        return "item/update";
    }

    @RequestMapping("/exUpdate")
    public String exUpdate(Item item, HttpServletRequest request, @RequestParam("file")CommonsMultipartFile[] files) throws IOException {
        itemCommon(item, request, files);
        itemService.updateById(item);
        return  "redirect:/item/findBySql.action";
    }

    private void itemCommon(Item item, HttpServletRequest request, @RequestParam("file") CommonsMultipartFile[] files) throws IOException {
        if (files.length>0){
            for (int s=0;s<files.length;s++ ){
                String n = UUIDUtils.create();
                String path = SystemContext.getRealPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename();
                File newFile = new File(path);

                files[s].transferTo(newFile);
                if (s==0){
                    item.setUrl1(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if (s==1){
                    item.setUrl2(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if (s==2){
                    item.setUrl3(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if (s==3){
                    item.setUrl4(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
                if (s==4){
                    item.setUrl5(request.getContextPath()+"\\resource\\ueditor\\upload\\"+n+files[s].getOriginalFilename());
                }
            }

        }

        ItemCategory byId = itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
    }

    @RequestMapping("/delete")
    public String update(Integer id){
        Item obj = itemService.load(id);
        obj.setIsDelete(1);
        itemService.updateById(obj);
        return "redirect:/item/findBySql.action";
    }

    /**
     * 展开二级分类
     */
    @RequestMapping("/shoplist")
    public String shopList(Item item,String condition,Model model){
        String sql = "select * from item where isDelete=0";
        if(!isEmpty(item.getCategoryIdTwo())){
            sql +=" and category_id_two = " +item.getCategoryIdTwo();
        }
        if(!isEmpty(condition)){
            sql += " and name like '%" + condition +"%' ";
            model.addAttribute("condition",condition);
        }
        if(!isEmpty(item.getPrice())){
            sql += " order by (price+0) desc";
        }
        if(!isEmpty(item.getGmNum())){
            sql += " order by gmNum desc";
        }
        if(isEmpty(item.getPrice())&&isEmpty(item.getGmNum())){
            sql += " order by id desc";
        }

        Pager<Item> pager = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pager);
        model.addAttribute("obj",item);
        return "item/shoplist";
    }

    @RequestMapping("/view")
    public String view(int id,Model model){
        Item obj = itemService.load(id);
        model.addAttribute("obj",obj);
        return "item/view";
    }
}
