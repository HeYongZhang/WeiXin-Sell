package com.example.sell.service.impl;

import com.example.sell.dto.CartDTO;
import com.example.sell.entity.ProductInfo;
import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.repository.ProductInfoRepository;
import com.example.sell.service.ProductService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



@Service
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(key = "#prudoctId")
    public ProductInfo findOne(String prudoctId) {
        return repository.findOne(prudoctId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }



    @Override
    @Caching(
            //cacheNames = "product",
            evict = {
                    @CacheEvict(key = "'all'"),
            },
            /*cacheable = {
                    //返回值无法获取
                    @Cacheable(cacheNames = "product",key = "#result.productId")
            }*/
            put = {
                    @CachePut(key = "#result.productId")
           }
    )
    public ProductInfo save(ProductInfo productInfo) {
        ProductInfo save = repository.save(productInfo);
        return  save;
    }





    //返回商品库存量
    @CacheEvict(cacheNames = {"product"})
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProduceStock() + cartDTO.getProductQuantity();
            productInfo.setProduceStock(result);
            repository.save(productInfo);
        }
    }

    @CacheEvict(cacheNames = {"product"})
    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //productInfo.setProduceStock(result)
            repository.save(productInfo);
        }
    }


    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",key = "'all'"),
            },
            put = {
                    @CachePut(value = "product",key = "#result.productId")
            }
    )
    @Override
    public ProductInfo OnSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            throw new SellException(ResultEnum.PRODUCT_IS_ONSALE);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",key = "'all'"),
            },
            put = {
                    @CachePut(value = "product",key = "#result.productId")
            }
    )
    @Override
    public ProductInfo Off_Sale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
            throw new SellException(ResultEnum.PRODUCT_IS_OFFSALE);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    @Override
    public void deleteType(Integer type) {
        repository.deleteByCategoryType(type);
    }
}
