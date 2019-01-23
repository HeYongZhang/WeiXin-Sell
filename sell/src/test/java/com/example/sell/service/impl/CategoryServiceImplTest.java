package com.example.sell.service.impl;

import com.example.sell.entity.ProductCategory;
import com.example.sell.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findOne() {
        ProductCategory one = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), one.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        Assert.assertNotEquals(0, all.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(3, 3));
        System.out.println(byCategoryTypeIn);
        assertNotEquals(0, byCategoryTypeIn.size());
    }

    @Test
    public void save() {
        ProductCategory one = categoryService.findOne(1);
        one.setCategoryType(2);
        ProductCategory save = categoryService.save(one);
        System.out.println(save);
    }
}