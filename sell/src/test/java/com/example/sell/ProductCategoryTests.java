package com.example.sell;

import com.example.sell.entity.ProductCategory;
import com.example.sell.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTests {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void findOne() {
        ProductCategory one = repository.findOne(1);
        System.out.println(one.toString());
    }

    /**
     * @Transactional 开启事物，在测试中执行完之后进行自动回滚
     * Assert：测试类提供的一个判断工具类
     */


    @Test
    //@Transactional
    public void insert() {
       /* ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCategoryId(1);
        productCategory1.setCategoryName("热销榜");
        productCategory1.setCategoryType(2);
        System.out.println("热销榜save"+repository.save(productCategory1));
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCategoryId(2);
        productCategory2.setCategoryName("女生最爱");
        productCategory2.setCategoryType(3);
        System.out.println("女生最爱save"+repository.save(productCategory2));*/
        ProductCategory one = repository.findOne(1);
        System.out.println(one.toString());
        one.setCategoryType(3);
        ProductCategory save = repository.save(one);
        Assert.assertNotNull(save);
    }

    ;


    @Test
    public void findByCategoryTypeInTest() {
       /* List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        System.out.println(byCategoryTypeIn);
        Assert.assertNotEquals(0,byCategoryTypeIn.size());*/
    }

    @Test
    public void get(){
        Object o = redisTemplate.opsForValue().get("all");
        System.out.println(o==null);
    }

}

