package com.webbious.ecommerce.services.interfaces;

import com.webbious.ecommerce.dtos.ProductDto;
import com.webbious.ecommerce.filters.ProductFilter;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();

    List<ProductDto> getFilteredProducts(ProductFilter filter);

    Long countFilteredProducts(ProductFilter filter);
}