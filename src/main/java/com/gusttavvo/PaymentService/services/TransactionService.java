package com.gusttavvo.PaymentService.services;

import com.gusttavvo.PaymentService.domain.transaction.Transaction;
import com.gusttavvo.PaymentService.domain.users.User;
import com.gusttavvo.PaymentService.dtos.TransactionDTO;
import com.gusttavvo.PaymentService.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

   public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.value());


        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        if (!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimeStamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
       this.notificationService.sendNotification(receiver, "Transação recebida com sucesso!");


       return transaction;
    }

    public boolean authorizeTransaction(User user, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
            Map<String, Object> data = (Map<String, Object>) authorizationResponse.getBody().get("data");

            if (data != null) {
                return (boolean) data.get("authorization");
            }
        }
        return false;
    }

}
