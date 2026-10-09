package com.codewithlei.vendora.users;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.users.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "profile_img")
    private String pfpUrl;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at" , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user")
    private SellerProfileEntity sellerProfile;

    @PrePersist
    void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void promoteToSeller(){
        if(role != Role.CUSTOMER){
            throw new IllegalStateException("Only customers can be promoted to seller.");
        }
        this.role = Role.SELLER;
    }

}
