package com.webbious.ecommerce.services.interfaces;

import com.webbious.ecommerce.entities.Product;
import com.webbious.ecommerce.filters.ProductFilter;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getByFilter(ProductFilter filter);
    Long count(ProductFilter filter);
}

