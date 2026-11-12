package com.codewithlei.vendora.seller.services;

import com.codewithlei.vendora.common.email.RequestEmailDTO;
import com.codewithlei.vendora.common.storage.ImageService;
import com.codewithlei.vendora.common.validation.SellerValidation;
import com.codewithlei.vendora.seller.SellerMapper;
import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.dto.newSeller.RequestNewSellerDTO;
import com.codewithlei.vendora.seller.dto.sellerData.ResponseSellerDataDTO;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.seller.exception.SellerAlreadyExistException;
import com.codewithlei.vendora.seller.exception.SellerProfileNotFound;
import com.codewithlei.vendora.users.UserEntity;
import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.enums.Role;
import com.codewithlei.vendora.users.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@Service
public class SellerService {
   private final SellerRepository sellerRepository;
   private final SellerValidation sellerValidation;
   private final UserRepository userRepository;
   private final ImageService imageService;
   private final SellerMapper sellerMapper;
   private final String defaultPfp;
   private final ApplicationEventPublisher eventPublisher;

    public SellerService(SellerRepository sellerRepository, SellerValidation sellerValidation,
                         UserRepository userRepository, ImageService imageService,
                         SellerMapper sellerMapper , @Value("${app.default.pfp}") String defaultPfp,
                         ApplicationEventPublisher eventPublisher) {
        this.sellerRepository = sellerRepository;
        this.sellerValidation = sellerValidation;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.sellerMapper = sellerMapper;
        this.defaultPfp = defaultPfp;
        this.eventPublisher = eventPublisher;
    }

    private static final Logger log = LoggerFactory.getLogger(SellerService.class);

    @Transactional(rollbackFor = Exception.class)
    public void apply(String email, RequestNewSellerDTO request) {
        sellerValidation.validateShopName(request.getShopName());

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if(user.getRole() != Role.CUSTOMER){
            throw new InvalidSellerStatusException("Only customers can request approval.");
        }

        if(sellerRepository.existsByUser(user)){
            throw new SellerAlreadyExistException("Seller has a pending application.");
        }
        SellerProfileEntity seller = sellerMapper.mapToEntity(user, request);
        seller.setSellerStatus(SellerStatus.PENDING);
        seller.setShopPfp(defaultPfp);
        sellerRepository.save(seller);
        eventPublisher.publishEvent(new RequestEmailDTO(user.getEmail() , user.getFirstname() , user.getLastname()));
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

   public ResponseSellerDataDTO applicationStatus(String email){
       UserEntity user = userRepository.findByEmail(email)
               .orElseThrow(UserNotFoundException::new);

       return sellerRepository.findByUser(user)
               .map(data -> ResponseSellerDataDTO.builder()
                       .shopPfp(data.getShopPfp())
                       .shopName(data.getShopName())
                       .description(data.getDescription())
                       .createdAt(data.getCreatedAt())
                       .sellerStatus(data.getSellerStatus())
                       .websiteLink(data.getWebsiteLink())
                       .shopPfp(data.getShopPfp())
                       .sellerCategory(data.getSellerCategory())
                       .build()
               ).orElseThrow(SellerProfileNotFound::new);
   }



}
