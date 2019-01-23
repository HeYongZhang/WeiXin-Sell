package com.example.sell.controller;

import com.example.sell.dto.OrderDTO;
import com.example.sell.entity.ProductCategory;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.CategoryFrom;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import com.lly835.bestpay.rest.type.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name="page",defaultValue = "1")Integer page,
                             @RequestParam(name = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        Pageable pageable = new PageRequest(page-1,size);
        Page<ProductCategory> categoryPage = categoryService.findList(pageable);
        map.put("categoryPage",categoryPage);
        map.put("currenPage",page);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        if(categoryId!=null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryFrom categoryFrom,BindingResult bindingResult,
                             Map<String,Object> map){
        map.put("url","/sell/seller/category/list");
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error");
        }

        ProductCategory productCategory = new ProductCategory();
       try {
           if(categoryFrom.getCategoryId()!=null){
               productCategory =  categoryService.findOne(categoryFrom.getCategoryId());
           }
           BeanUtils.copyProperties(categoryFrom,productCategory);
           categoryService.save(productCategory);
       }catch (SellException e){
            map.put("msg",e);
           return new ModelAndView("common/error");
       }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/del")
    public ModelAndView del(@RequestParam Integer categoryId,Map<String,String> map){
        map.put("url","/sell/seller/category/list");
        try {
            categoryService.delete(categoryId);
        }catch (SellException e){
            map.put("msg","类目不存在");
            return new ModelAndView("common/error");
        }
        map.put("msg","类目及类目下的商品清除成功");
        return new ModelAndView("common/success",map);
    }

    //          sell/seller/category/exit
    @RequestMapping(value = "/exit",method = RequestMethod.POST)
    @ResponseBody
    public String exit(@RequestParam String type){
         type = type.replace(",", "");
        ProductCategory category = categoryService.findByType(Integer.valueOf(type));
        if(category==null){
            return "true";
        }else{
            return "false";
        }
    }
}
