package com.codewithlei.vendora.common.validation;

import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.exception.SellerAlreadyExistException;
import com.codewithlei.vendora.users.exception.ProfileImageNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class SellerValidation {
    private final SellerRepository sellerRepository;

    public void validateSellerPfp(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new ProfileImageNullException();
        }
    }
    public void validateShopName(String shopName){
        if(sellerRepository.existsByShopName(shopName) || shopName.isEmpty()){
            throw new SellerAlreadyExistException("Seller already exist!");
        }
    }
}
