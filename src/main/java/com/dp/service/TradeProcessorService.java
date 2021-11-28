package com.dp.service;

import com.dp.TradeProcessorException;
import com.dp.model.Trade;
import com.dp.repository.TradeRepository;
import com.dp.validator.TradeValidator;
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
