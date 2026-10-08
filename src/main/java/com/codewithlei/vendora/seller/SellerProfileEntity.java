package com.codewithlei.vendora.seller;

import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seller_profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SellerProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id" , nullable = false , unique = true)
    private UserEntity user;

    @Column(name = "shop_pfp")
    private String shopPfp;

    @Column(name = "shop_name" , nullable = false , unique = true)
    private String shopName;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "seller_status")
    private SellerStatus sellerStatus;

    @Column(name = "created_at" , nullable = false)
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
    public void approve(){
        this.sellerStatus = SellerStatus.APPROVED;
    }

    public void suspend(){
        this.sellerStatus = SellerStatus.SUSPENDED;
    }
}
