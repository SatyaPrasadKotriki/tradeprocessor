package com.dp.repository;

import com.dp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    public List<Transaction> findByUserId(String userId);
}
