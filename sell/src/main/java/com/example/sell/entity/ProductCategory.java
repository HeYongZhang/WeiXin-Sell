package com.example.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 类目实体类
 * Created by 何泳璋
 * 2018/12/19 10:54
 */
@Table(name = "product_category")
@Entity
@DynamicUpdate      //动态更新
@Data
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 6922946870530320791L;

    /**
     * 类目 id
     */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**
     * 类目 name
     */
    private String categoryName;

    /**
     * 类目 type
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
