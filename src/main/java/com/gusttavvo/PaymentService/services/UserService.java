package com.gusttavvo.PaymentService.services;

import com.gusttavvo.PaymentService.domain.users.User;
import com.gusttavvo.PaymentService.domain.users.UserType;
import com.gusttavvo.PaymentService.dtos.UserDTO;
import com.gusttavvo.PaymentService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    void validateTransaction(User sender, BigDecimal amount) throws Exception {

        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Este tipo de usuário não tem permissão para realizar transações");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");

        }

    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }
}
