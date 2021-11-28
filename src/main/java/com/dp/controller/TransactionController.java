package com.dp.controller;

import com.dp.model.Transaction;
import com.dp.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository userRepository) {
        this.transactionRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        log.info("Fetching all users");
        return new ResponseEntity<>(transactionRepository.findAll(), OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable final String userId) {
        log.info("Fetching all users");
        return new ResponseEntity<>(transactionRepository.findByUserId(userId), OK);
    }

    @PostMapping
    public ResponseEntity<String> saveTransactions(@RequestBody List<Transaction> transactions) {
        log.info("saving transaction" + transactions);
        transactionRepository.saveAll(transactions);
        return new ResponseEntity<>("Transaction saved successfully ", OK);
    }

    @PutMapping
    public ResponseEntity<String> approveTransactions(@RequestBody List<Transaction> transactions) {
        log.info("saving transaction" + transactions);
        transactionRepository.saveAll(transactions);
        return new ResponseEntity<>("Transaction saved successfully ", OK);
    }
}
