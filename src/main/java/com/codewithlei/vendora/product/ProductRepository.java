package com.codewithlei.vendora.product;

import com.codewithlei.vendora.product.entity.ProductEntity;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findBySellerProfile(SellerProfileEntity sellerProfile ,
                                            Pageable pageable);
    Optional<ProductEntity> findBySellerProfileAndId(SellerProfileEntity sellerProfile, Long id);
}
