package com.spribe.currency.exchange.controller;

import com.spribe.currency.exchange.service.storage.CurrencyStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyRateController.class)
class CurrencyRateControllerIT {

    @MockBean
    private CurrencyStorageService currencyStorageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrencies_WhenCalled_thenReturnCurrencies() throws Exception {
        String testName = "TEST_NAME";
        String target = "TEST1";
        double rate = 123.45D;
        when(currencyStorageService.getRates(testName)).thenReturn(Map.of(target, rate));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currency-rates")
                        .param("source", testName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"source\":\"%s\",\"rates\":{\"%s\":%.2f}}".formatted(testName, target, rate)));
    }

}
