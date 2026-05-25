package com.gusttavvo.PaymentService.repository;

import com.gusttavvo.PaymentService.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {



}
