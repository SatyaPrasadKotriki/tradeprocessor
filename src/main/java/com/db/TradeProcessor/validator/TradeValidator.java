package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.TradeProcessorException;
import com.db.TradeProcessor.model.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TradeValidator {
    private final List<ExistingTradeValidator> existingTradeValidators;
    private final List<CurrentTradeValidator> currentTradeValidators;

    public TradeValidator(List<ExistingTradeValidator> existingTradeValidators, List<CurrentTradeValidator> currentTradeValidators) {
        this.existingTradeValidators = existingTradeValidators;
        this.currentTradeValidators = currentTradeValidators;
    }

    public void validateTrade(Trade trade, Optional<Trade> existingTrade) throws TradeProcessorException {
        List<String> errorMessages = new ArrayList<>();

        existingTrade.ifPresent(value -> existingTradeValidators.forEach(existingTradeValidator -> {
            try {
                existingTradeValidator.validate(trade, value);
            } catch (Exception e) {
                errorMessages.add(e.getMessage());
            }
        }));

        currentTradeValidators.forEach(currentTradeValidator -> {
            try {
                currentTradeValidator.validate(trade);
            } catch (Exception e) {
                errorMessages.add(e.getMessage());
            }
        });
        if (!errorMessages.isEmpty()) {
            throw new TradeProcessorException(errorMessages.toString());
        }
    }
}
