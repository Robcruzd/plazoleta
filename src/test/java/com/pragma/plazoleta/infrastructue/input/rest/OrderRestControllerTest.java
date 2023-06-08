package com.pragma.plazoleta.infrastructue.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;
import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderRestControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IOrderHandler orderHandler;

    private OrderRestController orderRestController;
    private String token;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
        token = "token";
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Cliente")
    void saveOrder_Success() throws Exception {
        Long restaurantId = 1L;
        List<OrderDishesRequestDto> orderDishesRequestDtoList = new ArrayList<>();
        orderDishesRequestDtoList.add(new OrderDishesRequestDto(1L, 2));
        orderDishesRequestDtoList.add(new OrderDishesRequestDto(2L, 1));
        OrderRequestDto orderRequestDto = new OrderRequestDto(restaurantId, orderDishesRequestDtoList);

        doNothing().when(orderHandler).saveOrder(orderRequestDto, token);

        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/order/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t")
    void saveOrder_ApplicationException() throws Exception {
        OrderRequestDto orderRequestDto = new OrderRequestDto(null, null);

        String errorMessage = "Información invalida de la orden";
        doThrow(new ApplicationException(errorMessage)).when(orderHandler).saveOrder(orderRequestDto, token);

        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/order/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto)));

        response.andDo(print())
                .andExpect(status().isForbidden());
    }
}