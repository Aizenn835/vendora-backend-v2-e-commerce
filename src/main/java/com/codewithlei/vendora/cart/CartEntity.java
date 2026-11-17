package com.codewithlei.vendora.cart;

import com.codewithlei.vendora.product.entity.ProductEntity;
import com.codewithlei.vendora.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY , optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false , updatable = false)
    private LocalDateTime addedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void added(){
        LocalDateTime now = LocalDateTime.now();
        this.addedAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void update(){
        this.updatedAt = LocalDateTime.now();
    }

}
