package com.spribe.currency.exchange.controller;

import com.spribe.currency.exchange.domain.entity.Currency;
import com.spribe.currency.exchange.service.dictionary.CurrencyDictionaryService;
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

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyController.class)
class CurrencyControllerIT {

    @MockBean
    private CurrencyDictionaryService currencyDictionaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrencies_WhenCalled_thenReturnCurrencies() throws Exception {
        String testName = "TEST_NAME";
        Currency currency = Currency.builder()
                .name(testName)
                .build();
        when(currencyDictionaryService.getAllCurrencies()).thenReturn(List.of(currency));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[%s]".formatted(testName)));
    }

    @Test
    void addCurrency_WhenCalled_thenReturnStatusAccepted() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/currencies/{code}", "TEST_CODE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(currencyDictionaryService).add("TEST_CODE");
    }

}
