package com.example.sell.repository;

import com.example.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional()
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        assertNotEquals(0, byProductStatus.size());
    }

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProduceStock(100);
        productInfo.setProductDescription("好吃到爆炸");
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setCategoryType(2);
        ProductInfo save = repository.save(productInfo);
        assertNotNull(save);
    }
}