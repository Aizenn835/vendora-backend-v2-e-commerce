package com.codewithlei.vendora.seller.dto.newSeller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestNewSellerDTO {
    @NotBlank(message = "Shop name cannot be blank")
    private String shopName;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
