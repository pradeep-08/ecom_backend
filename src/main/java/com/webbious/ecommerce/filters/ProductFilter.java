package com.webbious.ecommerce.filters;

import lombok.Data;

@Data
public class ProductFilter {
    private String name;
    private String category;
    private String sku;

    private String sortBy;     // "asc" or "desc"
    private String property;   // field to sort by

    private int page = 0;
    private int size = 10;
    private Long totalElements;

    public int getOffSet() {
        return page * size;
    }

    public int getPageSize() {
        return size;
    }
}

