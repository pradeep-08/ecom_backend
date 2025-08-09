package com.webbious.ecommerce.controllers;

import com.webbious.ecommerce.dtos.ProductDto;
import com.webbious.ecommerce.filters.ProductFilter;
import com.webbious.ecommerce.repositories.ProductRepository;
import com.webbious.ecommerce.services.interfaces.ProductService;
import com.webbious.ecommerce.utils.ApiResponse;
import com.webbious.ecommerce.utils.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto dto) {
        ProductDto created = productService.createProduct(dto);
        return ResponseEntity.ok(new ApiResponse<>("success", "Product created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDto updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("success", "Product updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Product deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Product fetched successfully", product));
    }


    @PostMapping("/list")
    public ResponseEntity<Report<ProductFilter, ProductDto>> getFilteredProductList(@RequestBody ProductFilter filter) {
        List<ProductDto> list = productService.getFilteredProducts(filter);
        filter.setTotalElements(productService.countFilteredProducts(filter));

        Report<ProductFilter, ProductDto> report = new Report<>();
        report.setContent(list);
        report.setFilterBy(filter);

        return ResponseEntity.ok(report);
    }
}
