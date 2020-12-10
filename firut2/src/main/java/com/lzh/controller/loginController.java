package com.lzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzh.base.BaseController;
import com.lzh.pojo.*;
import com.lzh.service.ItemCategoryService;
import com.lzh.service.ItemService;
import com.lzh.service.ManageService;
import com.lzh.service.UserService;
import com.lzh.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/login")
public class loginController extends BaseController {
   @Autowired
    ManageService manageService;

   @Autowired
   private ItemCategoryService itemCategoryService;
   @Autowired
   private ItemService itemService;
   @Autowired
   private UserService userService;
    @RequestMapping("/login")
    public String login() {
        return "/login/mLogin";
    }

    /**
     * 登陆验证
     */
    @RequestMapping("/toLogin")
    public String toLogin(Manage manage,HttpServletRequest request){
      Manage byEntity= manageService.getByEntity(manage);
      if (byEntity == null){
          return "redirect:/login/mtuichu";
      }
        request.getSession().setAttribute(Consts.MANAGE,manage);

        return "/login/mIndex";
    }

    /**
     * 管理员推出
     */
    @RequestMapping("/mtuichu")
    public String mtuichu(HttpServletRequest request) {
        request.getSession().setAttribute(Consts.MANAGE,null);
        return "/login/mLogin";

    }

    /**
     * 跳到前端
     */

    @RequestMapping("/uIndex")
    public String uIndex(Model model, Item item,HttpServletRequest request){
        String sql1 = "select * from item_category  where isDelete = 0 and pid is null order by name ";
        List<ItemCategory> itemCategories = itemCategoryService.listBySqlReturnEntity(sql1);
        ArrayList<CategoryDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(itemCategories)){
            for (ItemCategory ic :itemCategories){
                CategoryDto dto = new CategoryDto();
                dto.setFather(ic);
                String sql2 = "select * from item_category where isDelete=0 and pid="+ic.getId();
                List<ItemCategory> childrens = itemCategoryService.listBySqlReturnEntity(sql2);
                dto.setChildrens(childrens);
                list.add(dto);
                model.addAttribute("lbs",list);
            }
        }
        //折扣商品
        List<Item> zks = itemService.listBySqlReturnEntity("select * from item where isDelete=0 and zk is not null order by zk desc limit 0,10");
        model.addAttribute("zks",zks);

        //热销产品
        List<Item> rxs = itemService.listBySqlReturnEntity("select * from item where isDelete=0 order by gmNum desc limit 0,10");
        model.addAttribute("rxs",rxs);

        return "login/uIndex";
    }

    /**
     * 跳转注册页面
     */
    @RequestMapping("/res")
    public String res(){
        return "login/res";
    }

    @RequestMapping("/toRes")
    public String toRes(User user){
        userService.insert(user);
        return "login/uLogin";
    }

    /**
     * 跳到登录页面
     *
     */
    @RequestMapping("/uLogin")
    public String uLogin() {
        return "/login/uLogin";
    }

    @RequestMapping("/utoLogin")
    public String utoLogin(User user,HttpServletRequest request){
        User byEntity = userService.getByEntity(user);
        if (byEntity ==null){
            return "redirect:/login/res";
        }
        else {
            request.getSession().setAttribute("role",2);
            request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
            request.getSession().setAttribute(Consts.USERID,byEntity.getId());
            return "redirect:/login/uIndex";
        }
    }

    @RequestMapping("/uTui")
    public String uTui(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login/uIndex.action";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/pass")
    public String pass(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if (attribute == null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        request.setAttribute("obj",load);
        return "login/pass";
    }
    /**
     * 修改密码操作
     */
    @RequestMapping("/upass")
    @ResponseBody
    public String upass(String password,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        JSONObject js = new JSONObject();
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        load.setPassWord(password);
        userService.updateById(load);
        js.put(Consts.RES,1);
        return js.toString();
    }
}
