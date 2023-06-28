package com.pragma.plazoleta.infrastructue.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RestaurantRestControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IRestaurantHandler restaurantHandler;
    private String token;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
        token = "token";
    }

//    @Test
//    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Administrador")
//    void saveRestaurant_Success() throws Exception {
//        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
//        doNothing().when(restaurantHandler).saveRestaurant(restaurantRequestDto);
//
//        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/restaurant/")
//                .header("Authorization", "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(restaurantRequestDto)));
//
//        response.andDo(print())
//                .andExpect(status().isOk());
//    }
//
////    @Test
////    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Propietario")
////    void saveRestaurant_Failure()throws Exception {
////        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
////        ApplicationException exception = new ApplicationException("Error al guardar el restaurante");
////        doThrow(exception).when(restaurantHandler).saveRestaurant(restaurantRequestDto);
////
////        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/restaurant/")
////                .header("Authorization", "Bearer " + token)
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(restaurantRequestDto)));
////
////        response.andDo(print())
////                .andExpect(status().isForbidden());
////    }
//
//    @Test
//    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Propietario")
//    void findRestaurantById_Success() throws Exception {
//        Long restaurantId = 1L;
//        RestaurantRequestDto expectedDto = new RestaurantRequestDto();
//        when(restaurantHandler.findRestaurantById(restaurantId)).thenReturn(expectedDto);
//
//        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/restaurant/1")
//                .header("Authorization", "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andDo(print())
//                .andExpect(status().isOk());
//    }
//
////    @Test
////    void findRestaurantById_Failure() throws Exception {
////        Long restaurantId = 1L;
////        ApplicationException exception = new ApplicationException("Restaurante no encontrado");
////        when(restaurantHandler.findRestaurantById(restaurantId)).thenThrow(exception);
////
////        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/restaurant/1")
////                .header("Authorization", "Bearer " + token)
////                .contentType(MediaType.APPLICATION_JSON));
////
////        response.andDo(print())
////                .andExpect(status().isForbidden());
////    }
//
//    @Test
//    void findRestaurantLists_Success() throws Exception {
//        int page = 1;
//        int size = 3;
//        List<RestaurantListResponseDto> restaurantListResponseDtos = new ArrayList<>();
//
//        when(restaurantHandler.listRestaurants(page, size)).thenReturn(restaurantListResponseDtos);
//
//        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/restaurant/list?page=1&size=3")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findRestaurantLists_Failure() throws Exception {
//        int page = 1;
//        int size = 3;
//        ApplicationException exception = new ApplicationException("Restaurante no encontrado");
//        when(restaurantHandler.listRestaurants(page, size)).thenThrow(exception);
//
//        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/restaurant/list?page=1&size=3")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andDo(print())
//                .andExpect(status().isBadRequest());
//    }
}