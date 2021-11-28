package com.dp.service;

import com.dp.TradeProcessorException;
import com.dp.model.Trade;
import com.dp.repository.TradeRepository;
import com.dp.validator.TradeValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeProcessorServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TradeValidator tradeValidator;

    @InjectMocks
    private TradeProcessorService tradeProcessorService;

    @Test
    public void testGetAllTrades() {
        Trade expectedTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        when(tradeRepository.findAll()).thenReturn(singletonList(expectedTrade));
        List<Trade> actualTrades = tradeProcessorService.getAllTrades();
        assertThat(actualTrades).isEqualTo(singletonList(expectedTrade));
    }

    @Test
    public void testSaveTrade() throws TradeProcessorException {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        Optional<Trade> optional = Optional.empty();
        when(tradeRepository.findById(incomingTrade.getTradeId())).thenReturn(optional);
        doNothing().when(tradeValidator).validateTrade(incomingTrade, optional);
        tradeProcessorService.save(incomingTrade);
        verify(tradeRepository, times(1)).save(incomingTrade);
        verify(tradeValidator, times(1)).validateTrade(incomingTrade, optional);
    }

    @Test
    public void testSaveExistingTrade() throws TradeProcessorException {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B2", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        Optional<Trade> optional = Optional.of(existingTrade);
        when(tradeRepository.findById(incomingTrade.getTradeId())).thenReturn(optional);
        doNothing().when(tradeValidator).validateTrade(incomingTrade, optional);
        tradeProcessorService.save(incomingTrade);
        verify(tradeRepository, times(1)).save(incomingTrade);
        verify(tradeValidator, times(1)).validateTrade(incomingTrade, optional);
    }

    @Test
    public void testSave_MaturityDateValidationError() throws Exception {
        Trade incomingTrade = new Trade("T1", 2, "CP-1", "B2", LocalDate.parse("2019-09-20"), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        Optional<Trade> optional = Optional.of(existingTrade);
        when(tradeRepository.findById(incomingTrade.getTradeId())).thenReturn(optional);
        doThrow(new TradeProcessorException("Maturity should be greater than today's date")).when(tradeValidator).validateTrade(incomingTrade, optional);
        try {
            tradeProcessorService.save(incomingTrade);
        } catch (TradeProcessorException tradeProcessorException) {
            assertThat(tradeProcessorException.getMessage()).isEqualTo("Maturity should be greater than today's date");
        }
        verify(tradeRepository, times(0)).save(incomingTrade);
        verify(tradeValidator, times(1)).validateTrade(incomingTrade, optional);
    }
}