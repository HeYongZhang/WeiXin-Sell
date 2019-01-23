package com.example.sell.controller;

import com.example.sell.entity.ProductCategory;
import com.example.sell.entity.ProductInfo;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.ProductForm;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import com.example.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //  /sell/seller/product/list
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name="page",defaultValue = "1")Integer page,
                             @RequestParam(name = "size",defaultValue = "4") Integer size,
                             Map<String,Object> map){
        Pageable pageable = new PageRequest(page-1,size);
        Page<ProductInfo> productPage = productService.findAll(pageable);
        map.put("productPage",productPage);
        map.put("currenPage",page);
        return new ModelAndView("product/list",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView Off_sale(@RequestParam("productId") String productId,
                                 Map<String,Object> map){
        map.put("url","/sell/seller/product/list");
        try {
            productService.Off_Sale(productId);
        }catch (SellException e){
            log.error("【商品下架】商品存在异常{}",e);
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView On_sale(@RequestParam("productId") String productId,
                                Map<String,Object> map){
        map.put("url","/sell/seller/product/list");
        try {
            productService.OnSale(productId);
        }catch (SellException e){
            log.error("【商品上架】商品存在异常{}",e);
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }

    // /sell/seller/product/index
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name="productId",required = false)String productId,
                              Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有的类目
        List<ProductCategory> productCategory = categoryService.findAll();
        map.put("productCategoryList",productCategory);
        return new ModelAndView("product/index",map);
    }



    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm from, BindingResult bindingResult,
                             Map<String,Object> map){
        map.put("url","/sell/seller/product/index");
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error",map);
        }


        ProductInfo productInfo = new ProductInfo();
        try {
            if(!StringUtils.isEmpty(from.getProductId())){
                productInfo = productService.findOne(from.getProductId());
            }else{
                from.setProductId(KeyUtil.GetUniquekey());
            }
            BeanUtils.copyProperties(from,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
