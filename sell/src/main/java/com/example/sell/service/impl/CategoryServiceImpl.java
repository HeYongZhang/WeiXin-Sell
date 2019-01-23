package com.example.sell.service.impl;

import com.example.sell.entity.ProductCategory;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.repository.ProductCategoryRepository;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@CacheConfig(cacheNames = "category")
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductService productService;

    @Override
    @Cacheable(key = "#categoryId")
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    //买家端用于查询在架商品的所在类目的，业务复杂，不能改动
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> type) {
        return repository.findByCategoryTypeIn(type);
    }

    //类目的改变，会涉及一个或多个商品的改变w
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",allEntries = true),
                    @CacheEvict(key = "'all'")
            },
            put = {
                @CachePut(key = "#result.categoryId")
            }
    )
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public Page<ProductCategory> findList(Pageable pageable) {
        return repository.findAll(pageable);
    }



    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",allEntries = true),
                    @CacheEvict(key = "#categoryId"),
                    @CacheEvict(key = "'all'")
            }
    )
    public void delete(Integer categoryId) {
        ProductCategory category = repository.findOne(categoryId);
        if(category==null){
            log.error("【类目删除】 类目不存在，异常{}",categoryId);
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIT);
        }
        repository.delete(category.getCategoryId());
        productService.deleteType(category.getCategoryType());
    }

    @Override
    public ProductCategory findByType(Integer categoryType) {
        return repository.findByCategoryType(categoryType);
    }
}
