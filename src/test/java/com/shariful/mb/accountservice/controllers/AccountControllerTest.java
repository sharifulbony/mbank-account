package com.shariful.mb.accountservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import com.shariful.mb.accountservice.services.interfaces.CustomerInterface;
import com.shariful.mb.accountservice.entities.dtos.CustomerDto;
import org.junit.jupiter.api.Assertions;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    CustomerInterface customerInterface;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount() throws Exception {
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/account/create", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        CustomerDto.builder()
                                .customerId(1L)
                                .country("Estonia")
                                .currencies(Arrays.asList("EUR"))
                                .build()
                )))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mockMvcResult.getResponse().getContentAsString();
        AccountDto response = objectMapper.readValue(contentAsString, AccountDto.class);
        assertEquals(1L, response.getCustomerId());
        assertEquals(1, response.getBalances().size());
        Assertions.assertEquals("EUR", response.getBalances().get(0).getCurrency());
        Assertions.assertEquals(BigDecimal.ZERO, response.getBalances().get(0).getCurrentBalance());
    }

    @Test
    void getAccount() throws Exception {
        AccountDto createdAccountObject =
                customerInterface.createCustomer(
                        CustomerDto.builder()
                                .customerId(1L)
                                .country("Estonia")
                                .currencies(Arrays.asList("EUR"))
                                .build()
                );
        MvcResult mockMvcResult  =
                mockMvc.perform(get("/account/get/" + createdAccountObject.getAccountId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balances[0].currency", is("EUR"))
                ).andReturn();
        String contentAsString = mockMvcResult.getResponse().getContentAsString();
        AccountDto response = objectMapper.readValue(contentAsString, AccountDto.class);
        assertEquals(response.getAccountId(), createdAccountObject.getAccountId());
        assertEquals(response.getCustomerId(), createdAccountObject.getCustomerId());
        assertEquals(response.getBalances().size(), createdAccountObject.getBalances().size());
        Assertions.assertEquals("EUR", response.getBalances().get(0).getCurrency());
    }
}
