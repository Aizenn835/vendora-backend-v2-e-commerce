package com.codewithlei.vendora.product.services;

import com.codewithlei.vendora.common.storage.ImageService;
import com.codewithlei.vendora.common.validation.ProductValidation;
import com.codewithlei.vendora.product.ProductEntity;
import com.codewithlei.vendora.product.ProductMapper;
import com.codewithlei.vendora.product.ProductRepository;
import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.seller.exception.SellerProfileNotFound;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SellerProductService {
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private static final Logger log = LoggerFactory.getLogger(SellerProductService.class);

    @Transactional(rollbackFor = Exception.class)
    public void addProduct(MultipartFile file, String email, RequestNewProductDTO request) throws IOException {
        productValidation.validateProductImg(file);

        SellerProfileEntity seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(SellerProfileNotFound::new);

        if(seller.getSellerStatus() != SellerStatus.APPROVED){
            throw new InvalidSellerStatusException("Only approved sellers can add products.");
        }

        String imgUrl = imageService.upload(file, "products");
        deleteImageIfRolledBack(imgUrl);

        ProductEntity product = productMapper.mapToEntity(seller, imgUrl, request);
        productRepository.save(product);
    }

    private void deleteImageIfRolledBack(String imgUrl) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_ROLLED_BACK) {
                    try {
                        imageService.deleteImage(imgUrl);
                    } catch (RuntimeException e) {
                        log.warn("Failed to delete orphaned image {}", imgUrl, e);
                    }
                }
            }
        });
    }

}
