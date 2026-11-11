package com.codewithlei.vendora.seller;

import com.codewithlei.vendora.seller.dto.newSeller.RequestNewSellerDTO;
import com.codewithlei.vendora.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    public SellerProfileEntity mapToEntity( UserEntity user, RequestNewSellerDTO request){
        return SellerProfileEntity.builder()
                .user(user)
                .shopName(request.getShopName())
                .phoneNumber(request.getPhoneNumber())
                .description(request.getDescription())
                .websiteLink(request.getWebsiteLink())
                .description(request.getDescription())
                .sellerCategory(request.getSellerCategory())
                .build();
    }
}
