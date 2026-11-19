package com.codewithlei.vendora.product.entity;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "seller_id" , nullable = false)
    private SellerProfileEntity sellerProfile;

    @Builder.Default
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantEntity> variant = new ArrayList<>();

    // this can be useful if later ill add multiple image for the products
    @Builder.Default
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<ProductImageEntity> imageLink = new ArrayList<>();

    @Column(name = "product_img")
    private String productImgUrl;

    @Column(name = "product_name" , nullable = false)
    private String productName;

    @Column(nullable = false , length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void addVariant(String size , int stock){
        ProductVariantEntity variants = ProductVariantEntity.builder()
                .product(this)
                .stock(stock)
                .size(size)
                .build();

        this.variant.add(variants);
    }
    public void addImage(String imageLink){
        ProductImageEntity image = ProductImageEntity.builder()
                .product(this)
                .imageLink(imageLink)
                .build();

        this.imageLink.add(image);
    }
    public void update(String productName , String description , BigDecimal price){
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
    public void reactivate(){
        this.isActive = true;
    }
    public void deactivate(){
        this.isActive = false;
    }
    public void updateImg(String productImgUrl){
        this.productImgUrl = productImgUrl;
    }
    public void removeVariant(ProductVariantEntity variant){
        this.variant.remove(variant);
    }
}
