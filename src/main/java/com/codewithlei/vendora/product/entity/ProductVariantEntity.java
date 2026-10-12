package com.codewithlei.vendora.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variant" ,
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "size"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "product_id" , nullable = false)
    private ProductEntity product;

    @Column(nullable = false , length = 50)
    private String size;

    @Column(nullable = false)
    private int stock;

    public void updateStock(int stock){
        this.stock = stock;
    }
}
