package com.codewithlei.vendora.seller.dto.newSeller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestNewSellerDTO {
    @NotBlank(message = "Shop name cannot be blank")
    private String shopName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 32 , message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9!@#$%^&*(),.?\":{}|<>]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=!)"
    )
    private String password;
}
