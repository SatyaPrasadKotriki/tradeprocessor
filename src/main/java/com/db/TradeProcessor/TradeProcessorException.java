package com.db.TradeProcessor;

public class TradeProcessorException extends Exception {

    private final String errorMessage;

    public TradeProcessorException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;

    }
}
