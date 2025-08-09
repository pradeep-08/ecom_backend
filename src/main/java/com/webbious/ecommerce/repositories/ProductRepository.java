package com.webbious.ecommerce.repositories;

import com.webbious.ecommerce.entities.Product;
import com.webbious.ecommerce.services.interfaces.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> , ProductRepositoryCustom {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.id = :id")
    Optional<Product> findByIdWithImages(@Param("id") Long id);
}
