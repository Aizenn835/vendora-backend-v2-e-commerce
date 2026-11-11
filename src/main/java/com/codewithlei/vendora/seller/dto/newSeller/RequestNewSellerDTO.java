package com.codewithlei.vendora.seller.dto.newSeller;

import com.codewithlei.vendora.seller.enums.SellerCategory;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class RequestNewSellerDTO {

    @NotBlank(message = "Shop name cannot be blank")
    private String shopName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank
    @Pattern(regexp = "^09\\d{9}$", message = "Phone number must be a valid 11-digit Philippine mobile number starting with 09")
    private String phoneNumber;


    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "The link cannot be empty")
    @URL(message = "Please provide a valid URL link")
    private String websiteLink;

    private SellerCategory sellerCategory;

}
