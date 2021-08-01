package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.TradeProcessorException;
import com.db.TradeProcessor.model.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TradeValidatorTest {
    private List<ExistingTradeValidator> existingTradeValidators = new ArrayList<>();
    private List<CurrentTradeValidator> currentTradeValidators = new ArrayList<>();
    @Mock
    private MaturityDateValidator maturityDateValidator;
    @Mock
    private VersionValidator versionValidator;

    @InjectMocks
    private TradeValidator tradeValidator = new TradeValidator(existingTradeValidators, currentTradeValidators);

    @Test
    public void testValidateTrade() throws TradeProcessorException {
        Trade incomingTrade = new Trade("T1", 3, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        currentTradeValidators.add(maturityDateValidator);
        existingTradeValidators.add(versionValidator);
        doNothing().when(maturityDateValidator).validate(incomingTrade);
        doNothing().when(versionValidator).validate(incomingTrade, existingTrade);
        tradeValidator.validateTrade(incomingTrade, Optional.of(existingTrade));
        verify(versionValidator, times(1)).validate(incomingTrade, existingTrade);
        verify(maturityDateValidator, times(1)).validate(incomingTrade);
    }

    @Test
    public void testValidateTrade_exceptionCase() {
        Trade incomingTrade = new Trade("T1", 3, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        currentTradeValidators.add(maturityDateValidator);
        existingTradeValidators.add(versionValidator);
        doThrow(new RuntimeException("Maturity should be greater than today's date")).when(maturityDateValidator).validate(incomingTrade);
        doThrow(new RuntimeException("Trade version is obsolete")).when(versionValidator).validate(incomingTrade, existingTrade);
        try {
            tradeValidator.validateTrade(incomingTrade, Optional.of(existingTrade));
        } catch (TradeProcessorException tradeProcessorException) {
            assertThat(tradeProcessorException.getMessage()).isEqualTo("[Trade version is obsolete, Maturity should be greater than today's date]");
        }
        verify(versionValidator, times(1)).validate(incomingTrade, existingTrade);
        verify(maturityDateValidator, times(1)).validate(incomingTrade);
    }
}