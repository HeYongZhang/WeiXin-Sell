package com.example.sell.repository;

import com.example.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface  ProductInfoRepository extends JpaRepository<ProductInfo, String> {

     List<ProductInfo> findByProductStatus(Integer id);


     void deleteByCategoryType(Integer categoryType);
}
