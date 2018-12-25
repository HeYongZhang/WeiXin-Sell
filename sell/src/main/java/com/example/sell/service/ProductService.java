package com.example.sell.service;

import com.example.sell.dto.CartDTO;
import com.example.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    /** 查询单个 */
    ProductInfo findOne(String prudoctId);

    /**
     *  查询所有在架的商品
     * @param
     * @return
     */
    List<ProductInfo> findUpAll();

    /** 查询所有 */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 增加商品 */
    ProductInfo save(ProductInfo productInfo);

    //商品库存增加
    void increaseStock(List<CartDTO> cartDTOList);

    //商品库存减少
    void decreaseStock(List<CartDTO> cartDTOList);
}
