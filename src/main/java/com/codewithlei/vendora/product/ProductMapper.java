package com.codewithlei.vendora.product;

import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import com.codewithlei.vendora.product.dto.newProduct.RequestProductUpdateDTO;
import com.codewithlei.vendora.product.dto.productData.ResponseProductDTO;
import com.codewithlei.vendora.product.dto.productData.ResponseVariantDTO;
import com.codewithlei.vendora.product.entity.ProductEntity;
import com.codewithlei.vendora.product.entity.ProductVariantEntity;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductEntity mapToEntity(SellerProfileEntity seller , String productImg , RequestNewProductDTO request){
        return ProductEntity.builder()
                .sellerProfile(seller)
                .productImgUrl(productImg)
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
    public ResponseProductDTO mapToDTO(ProductEntity product){
        List<ResponseVariantDTO> variant = product.getVariant()
                .stream()
                .map(this::mapToVariantResponse)
                .toList();

        return ResponseProductDTO.builder()
                .id(product.getId())
                .productImgUrl(product.getProductImgUrl())
                .variants(variant)
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isActive(product.getIsActive())
                .build();
    }

    private ResponseVariantDTO mapToVariantResponse(ProductVariantEntity variant) {
        return ResponseVariantDTO.builder()
                .id(variant.getId())
                .size(variant.getSize())
                .stock(variant.getStock())
                .build();
    }
}
