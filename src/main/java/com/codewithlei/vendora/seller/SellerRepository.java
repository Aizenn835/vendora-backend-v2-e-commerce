package com.codewithlei.vendora.seller;

import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<SellerProfileEntity, Long> {
    Boolean existsByShopName(String shopName);
    Optional<SellerProfileEntity> findByUser(UserEntity user);
    Optional<SellerProfileEntity> findByUser_Email(String userEmail);
    Page<SellerProfileEntity> findBySellerStatusOrderByCreatedAtDesc(SellerStatus sellerStatus , Pageable pageable);
}