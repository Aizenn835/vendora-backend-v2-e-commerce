package com.codewithlei.vendora.common.validation;

import com.codewithlei.vendora.product.exception.NullProductImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidation {

    public void validateProductImg(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new NullProductImageException();
        }
    }
}
