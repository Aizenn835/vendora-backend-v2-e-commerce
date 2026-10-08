package com.codewithlei.vendora.seller.dto.sellerData;

import com.codewithlei.vendora.seller.enums.SellerStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseSellerDataDTO {
    private String shopPfp;
    private String shopName;
    private String description;
    private SellerStatus sellerStatus;
    private LocalDateTime createdAt;
}
