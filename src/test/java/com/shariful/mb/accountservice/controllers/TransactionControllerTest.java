package com.shariful.mb.accountservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import com.shariful.mb.accountservice.services.interfaces.AccountInterface;
import com.shariful.mb.accountservice.services.interfaces.CustomerInterface;
import com.shariful.mb.accountservice.services.interfaces.TransactionInterface;
import com.shariful.mb.accountservice.entities.dtos.CustomerDto;
import com.shariful.mb.accountservice.utilities.util.TransactionSourceDestination;
import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private AccountInterface accountInterface;

    @Autowired
    private CustomerInterface customerInterface;

    @Autowired
    private TransactionInterface transactionInterface;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTransaction() throws Exception {
        AccountDto createdAccountObject =
                customerInterface.createCustomer(
                        CustomerDto.builder()
                                .customerId(1L)
                                .country("Estonia")
                                .currencies(Arrays.asList("EUR"))
                                .build()
                );
        MvcResult mockMvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/transaction/create", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(
                                TransactionDto.builder()
                                        .accountId(createdAccountObject.getAccountId())
                                        .amount(BigDecimal.valueOf(50L))
                                        .currency("EUR")
                                        .transactionSourceDestination(TransactionSourceDestination.IN)
                                        .description("Cash in")
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency", is("EUR")))
                .andReturn();
        String contentAsString = mockMvcResult.getResponse().getContentAsString();
        TransactionDto response = objectMapper.readValue(contentAsString, TransactionDto.class);
        assertEquals(response.getAccountId(), createdAccountObject.getAccountId());
        assertEquals(response.getAmount(), BigDecimal.valueOf(50L));
        assertEquals(response.getTransactionSourceDestination(), TransactionSourceDestination.IN);
    }

    @Test
    void getTransactions() throws Exception {
        AccountDto accountOperation =
                customerInterface.createCustomer(
                        CustomerDto.builder()
                                .customerId(1L)
                                .country("Estonia")
                                .currencies(Arrays.asList("EUR"))
                                .build()
                );

        TransactionDto transactionDto =
                TransactionDto.builder()
                        .accountId(accountOperation.getAccountId())
                        .amount(BigDecimal.valueOf(50L))
                        .currency("EUR")
                        .transactionSourceDestination(TransactionSourceDestination.IN)
                        .description("Cash in")
                        .build();

        TransactionDto savedTransactionDto = transactionInterface.createTransaction(transactionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/transaction/get/" + savedTransactionDto.getAccountId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].currency", is("EUR")))
                .andExpect(jsonPath("$.[0].description", is("Cash in")));

    }
}
