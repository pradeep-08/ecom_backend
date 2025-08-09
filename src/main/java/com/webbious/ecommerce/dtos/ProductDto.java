package com.webbious.ecommerce.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String sku;
    private String category;
    private String imageUrl;
    private List<String> images;
    private Date createdAt;
    private Date updatedAt;

}
