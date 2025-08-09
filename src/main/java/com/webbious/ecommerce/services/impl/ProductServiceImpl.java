package com.webbious.ecommerce.services.impl;

import com.webbious.ecommerce.dtos.ProductDto;
import com.webbious.ecommerce.entities.Product;
import com.webbious.ecommerce.exceptions.ResourceNotFoundException;
import com.webbious.ecommerce.filters.ProductFilter;
import com.webbious.ecommerce.repositories.ProductRepository;
import com.webbious.ecommerce.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSku(product.getSku());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        List<String> imageList = new ArrayList<>(product.getImages());
        dto.setImages(imageList);
        dto.setCreatedAt(product.getDateCreated());
        dto.setUpdatedAt(product.getDateUpdated());
        return dto;
    }

    private Product mapToEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setSku(dto.getSku());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setImages(dto.getImages());
        return product;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapToEntity(productDto);
        return mapToDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setSku(dto.getSku());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setImages(dto.getImages());

        return mapToDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return productRepository.findByIdWithImages(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getFilteredProducts(ProductFilter filter) {
        List<Product> products = productRepository.getByFilter(filter);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countFilteredProducts(ProductFilter filter) {
        return productRepository.count(filter);
    }
}