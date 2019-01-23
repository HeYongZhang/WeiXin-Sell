package com.example.sell.service;

import com.example.sell.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    public ProductCategory findOne(Integer categoryId);

    public List<ProductCategory> findAll();

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> type);

    public ProductCategory save(ProductCategory productCategory);

    public Page<ProductCategory> findList(Pageable pageable);

    //删除类目
    void delete(Integer categoryId);

    ProductCategory findByType(Integer categoryType);

}
