package com.lzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzh.base.BaseController;
import com.lzh.pojo.Car;
import com.lzh.pojo.Item;
import com.lzh.service.CarService;
import com.lzh.service.ItemService;
import com.lzh.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 购物车
 */
@Controller
@RequestMapping("/car")
public class CarController extends BaseController {
    @Autowired
    private CarService carService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/exAdd")
    public String exAdd(Car car, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if (attribute ==null){
            jsonObject.put(Consts.RES,0);
            return jsonObject.toJSONString();

        }
        //保存到购物车
        Integer userid = Integer.valueOf(attribute.toString());
        car.setUserId(userid);
        Item item = itemService.load(car.getItemId());
        String price = item.getPrice();
        Double valueOf = Double.valueOf(price);
        car.setPrice(valueOf);
        if (item.getZk()!=null){
            valueOf= valueOf * item.getZk() / 10;
            BigDecimal bg = new BigDecimal(valueOf).setScale(2, RoundingMode.UP);
            car.setPrice(bg.doubleValue());
            valueOf = bg.doubleValue();
        }
        Integer num = car.getNum();
        double t = valueOf * num;
        BigDecimal bg = new BigDecimal(t).setScale(2, RoundingMode.UP);
        double doubleValue = bg.doubleValue();
        car.setTotal(doubleValue+"");
        carService.insert(car);
        jsonObject.put(Consts.RES,1);
        return jsonObject.toJSONString();


    }

    @RequestMapping("/findBySql")
    public String findBySql(Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if (attribute == null){
            return "redirect:/login/toLogin";
        }
        Integer userid = Integer.valueOf(attribute.toString());
        String sql = "select * from car where user_id="+userid+ " order by id desc";
        List<Car> cars = carService.listBySqlReturnEntity(sql);
        model.addAttribute("list",cars);
        return "car/car";
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        carService.deleteById(id);
        return "success";
    }
}
