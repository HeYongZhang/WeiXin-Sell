package com.example.sell.repository;

import com.example.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    public List<ProductInfo> findByProductStatus(Integer id);
}
