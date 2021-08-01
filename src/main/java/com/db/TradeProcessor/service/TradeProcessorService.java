package com.db.TradeProcessor.service;

import com.db.TradeProcessor.TradeProcessorException;
import com.db.TradeProcessor.model.Trade;
import com.db.TradeProcessor.repository.TradeRepository;
import com.db.TradeProcessor.validator.TradeValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeProcessorService {

    private final TradeRepository tradeRepository;
    private final TradeValidator tradeValidator;

    public TradeProcessorService(TradeRepository tradeRepository, TradeValidator tradeValidationUtil) {
        this.tradeRepository = tradeRepository;
        this.tradeValidator = tradeValidationUtil;
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public void save(Trade trade) throws TradeProcessorException {
        tradeValidator.validateTrade(trade, tradeRepository.findById(trade.getTradeId()));
        tradeRepository.save(trade);
    }
}
