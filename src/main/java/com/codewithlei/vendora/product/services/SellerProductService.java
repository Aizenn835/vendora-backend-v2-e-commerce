package com.codewithlei.vendora.product.services;

import com.codewithlei.vendora.common.storage.ImageService;
import com.codewithlei.vendora.common.validation.ProductValidation;
import com.codewithlei.vendora.common.validation.UserValidation;
import com.codewithlei.vendora.product.dto.newProduct.RequestProductUpdateDTO;
import com.codewithlei.vendora.product.entity.ProductEntity;
import com.codewithlei.vendora.product.ProductMapper;
import com.codewithlei.vendora.product.ProductRepository;
import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import com.codewithlei.vendora.product.dto.productData.ResponseProductDTO;
import com.codewithlei.vendora.product.exception.ProductNotFoundException;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.seller.exception.SellerProfileNotFound;
import com.codewithlei.vendora.users.UserEntity;
import com.codewithlei.vendora.users.UserMapper;
import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserValidation userValidation;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private static final Logger log = LoggerFactory.getLogger(SellerProductService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        request.getVariants().forEach(var -> product.addVariant(var.getSize() , var.getStock()));

        productRepository.saveAndFlush(product);
    }

    @Transactional(readOnly = true)
    public Page<ResponseProductDTO> viewSellerProduct(String email  , Pageable pageable){
        SellerProfileEntity seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(SellerProfileNotFound::new);

        if(seller.getSellerStatus() != SellerStatus.APPROVED){
            throw new InvalidSellerStatusException("Seller account is not approved");
        }

        return productRepository.findBySellerProfile(seller , pageable)
                .map(productMapper::mapToDTO);
    }

    @Transactional(readOnly = true)
    public ResponseProductDTO viewSellerProductById(String email , Long id){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userValidation.validateRole(user);

        SellerProfileEntity seller = sellerRepository.findByUser(user)
                .orElseThrow(SellerProfileNotFound::new);

        ProductEntity product = productRepository.findBySellerProfileAndId(seller , id)
                .orElseThrow(ProductNotFoundException::new);

        return productMapper.mapToDTO(product);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateSellerProduct(String email , Long id , RequestProductUpdateDTO request){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userValidation.validateRole(user);

        SellerProfileEntity seller = sellerRepository.findByUser(user)
                .orElseThrow(SellerProfileNotFound::new);

        ProductEntity product = productRepository.findBySellerProfileAndId(seller , id)
                .orElseThrow(ProductNotFoundException::new);

        product.update(request.getName(),
                       request.getDescription(),
                       request.getPrice());
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateImage(String email , Long id , MultipartFile file)throws IOException{
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userValidation.validateRole(user);

        SellerProfileEntity seller = sellerRepository.findByUser(user)
                .orElseThrow(SellerProfileNotFound::new);

        ProductEntity product = productRepository.findBySellerProfileAndId(seller , id)
                .orElseThrow(ProductNotFoundException::new);

        imageService.deleteImage(product.getProductImgUrl());

        String imgUrl = imageService.upload(file , "products");
        deleteImageIfRolledBack(imgUrl);

        product.updateImg(imgUrl);
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
