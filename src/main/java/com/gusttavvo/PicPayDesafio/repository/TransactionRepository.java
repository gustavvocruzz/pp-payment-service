package com.gusttavvo.PicPayDesafio.repository;

import com.gusttavvo.PicPayDesafio.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {



}
