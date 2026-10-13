package com.codewithlei.vendora.common.validation;

import com.codewithlei.vendora.product.repository.ProductRepository;
import com.codewithlei.vendora.product.exception.NullProductImageException;
import com.codewithlei.vendora.product.exception.ProductAlreadyExistException;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ProductValidation {
    private final ProductRepository productRepository;

    public void validateProductImg(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new NullProductImageException();
        }
    }
    public void validateProductName(String shopName , SellerProfileEntity seller){
        if(productRepository.existsByProductNameAndSellerProfile(shopName , seller)){
            throw new ProductAlreadyExistException();
        }
    }
}
