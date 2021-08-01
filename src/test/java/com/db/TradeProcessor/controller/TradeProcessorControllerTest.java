package com.db.TradeProcessor.controller;

import com.db.TradeProcessor.TradeProcessorException;
import com.db.TradeProcessor.model.Trade;
import com.db.TradeProcessor.service.TradeProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class TradeProcessorControllerTest {

    @Mock
    private TradeProcessorService tradeProcessorService;
    @InjectMocks
    private TradeProcessorController tradeProcessorController;

    @Test
    public void testGetAllTrades() {
        Trade expectedTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        when(tradeProcessorService.getAllTrades()).thenReturn(singletonList(expectedTrade));
        ResponseEntity<List<Trade>> allTrades = tradeProcessorController.getAllTrades();
        assertThat(allTrades.getStatusCode()).isEqualTo(OK);
        assertThat(allTrades.getBody()).isEqualTo(singletonList(expectedTrade));
    }

    @Test
    public void testSaveTrade() throws TradeProcessorException {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        doNothing().when(tradeProcessorService).save(incomingTrade);
        ResponseEntity<String> actualResponseEntity = tradeProcessorController.saveTrade(incomingTrade);
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Trade saved successfully ", OK);
        assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
        verify(tradeProcessorService, times(1)).save(incomingTrade);
    }

    @Test
    public void testSaveTrade_invalidMaturityDate() throws TradeProcessorException {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
        doThrow(new TradeProcessorException("Maturity should be greater than today's date")).when(tradeProcessorService).save(incomingTrade);
        ResponseEntity<String> actualResponseEntity = tradeProcessorController.saveTrade(incomingTrade);
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Trade is not valid: \nMaturity should be greater than today's date", BAD_REQUEST);
        assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
        verify(tradeProcessorService, times(1)).save(incomingTrade);
    }
}