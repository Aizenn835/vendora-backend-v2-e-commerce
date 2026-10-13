package com.codewithlei.vendora.product.repository;

import com.codewithlei.vendora.product.entity.ProductEntity;
import com.codewithlei.vendora.product.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity , Long> {
    Optional<ProductVariantEntity> findByIdAndProduct(Long id , ProductEntity product);

    Integer countByProduct(ProductEntity product);
}
