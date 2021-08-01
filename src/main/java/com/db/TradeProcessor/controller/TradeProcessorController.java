package com.db.TradeProcessor.controller;

import com.db.TradeProcessor.TradeProcessorException;
import com.db.TradeProcessor.model.Trade;
import com.db.TradeProcessor.service.TradeProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/trades")
@Slf4j
public class TradeProcessorController {

    private final TradeProcessorService tradeProcessorService;

    public TradeProcessorController(TradeProcessorService tradeProcessorService) {
        this.tradeProcessorService = tradeProcessorService;
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        log.info("Fetching all trades");
        return new ResponseEntity<>(tradeProcessorService.getAllTrades(), OK);
    }

    @PostMapping
    public ResponseEntity<String> saveTrade(@RequestBody Trade trade) {
        try {
            log.info("saving trade" + trade);
            tradeProcessorService.save(trade);
            return new ResponseEntity<>("Trade saved successfully ", OK);
        } catch (TradeProcessorException e) {
            log.error("Error occurred while saving trade" + e.getMessage());
            return new ResponseEntity<>("Trade is not valid: \n" + e.getMessage(), BAD_REQUEST);
        }
    }
}
