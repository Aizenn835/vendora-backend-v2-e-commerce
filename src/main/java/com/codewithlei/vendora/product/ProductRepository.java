package com.codewithlei.vendora.product;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity , Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.isActive = true AND p.sellerProfile =:seller ORDER BY p.createdAt DESC")
    Page<ProductEntity> findByIsActive(@Param("seller") SellerProfileEntity seller , Pageable pageable);
}
