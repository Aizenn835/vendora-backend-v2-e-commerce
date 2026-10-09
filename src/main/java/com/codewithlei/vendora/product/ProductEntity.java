package com.codewithlei.vendora.product;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "product_img")
    private String productImgUrl;

    @Column(name = "product_name" , nullable = false)
    private String productName;

    @Column(nullable = false , length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

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

    public void reactivate(){
        this.isActive = true;
    }

    public void deactivate(){
        this.isActive = false;
    }

}
