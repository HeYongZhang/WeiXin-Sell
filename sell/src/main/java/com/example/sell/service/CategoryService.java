package com.example.sell.service;

import com.example.sell.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    public ProductCategory findOne(Integer categoryId);

    public List<ProductCategory> findAll();

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> type);

    public ProductCategory save(ProductCategory productCategory);
}
