package com.codewithlei.vendora.seller;

import com.codewithlei.vendora.seller.dto.newSeller.RequestNewSellerDTO;
import com.codewithlei.vendora.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    public SellerProfileEntity mapToDTO(String shopPfp , UserEntity user, RequestNewSellerDTO request){
        return SellerProfileEntity.builder()
                .user(user)
                .shopPfp(shopPfp)
                .shopName(request.getShopName())
                .description(request.getDescription())
                .build();
    }
}
