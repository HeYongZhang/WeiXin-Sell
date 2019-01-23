package com.example.sell.from;

import lombok.Data;

@Data
public class CategoryFrom {
    /**
     * 类目 id
     */
    private Integer categoryId;

    /**
     * 类目 name
     */
    private String categoryName;

    /**
     * 类目 type
     */
    private Integer categoryType;
}
