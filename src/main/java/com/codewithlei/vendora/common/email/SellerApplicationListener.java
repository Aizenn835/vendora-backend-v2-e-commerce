package com.codewithlei.vendora.common.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SellerApplicationListener {
    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handler(RequestEmailDTO request){
        emailService.sendSellerApplicationConfirmation(request.firstname(), request.lastname() , request.email());
    }
}
