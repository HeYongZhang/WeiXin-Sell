package com.example.sell.controller;

import com.example.sell.VO.ProductInfoVO;
import com.example.sell.VO.ProductVO;
import com.example.sell.VO.ResultVO;
import com.example.sell.entity.ProductCategory;
import com.example.sell.entity.ProductInfo;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import com.example.sell.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResultVO list(){
        //1.查询所有上架商品
        List<ProductInfo> productUpList = productService.findUpAll();

        //2.查询productUpList中存在类目，使用java8新增特性lambda进行循环
        List<Integer> collect = productUpList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryByTypeList = categoryService.findByCategoryTypeIn(collect);
        //拼接
        List<ProductVO> productVOList = new ArrayList<>();

        for (ProductCategory productCategory : categoryByTypeList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

           List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productUpList){
                if(productInfo.getCategoryType()==productCategory.getCategoryType()){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //将productInfo中和productInfoVo相同内容copy到productInfoVO
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        ResultVO success = ResultVOUtil.success(productVOList);
        return success;
    }
}
