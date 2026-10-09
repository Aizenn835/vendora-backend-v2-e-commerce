package com.codewithlei.vendora.product;

import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity mapToEntity(SellerProfileEntity seller , String productImg , RequestNewProductDTO request){
        return ProductEntity.builder()
                .sellerProfile(seller)
                .productImgUrl(productImg)
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }
}
