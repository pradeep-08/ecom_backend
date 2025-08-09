package com.webbious.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private BigDecimal price;

    private int stock;

    private String sku;

    private String category;

    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date dateCreated;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateUpdated;

    private boolean active = true;
}
