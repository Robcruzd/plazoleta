package com.pragma.plazoleta.infrastructue.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.plazoleta.application.dto.request.DishEnableDisableRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IDishHandler;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DishRestControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IDishHandler dishHandler;

    private DishRestController dishRestController;
    private String token;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()).build();
        token = "token";
    }
    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Propietario")
    void saveDish_Success() throws Exception {
        DishRequestDto dishRequestDto = new DishRequestDto("pasta", 1000, "plato de pasta", "https://pasta", 1L, 1L);//        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Plato creado exitosamente");
        doNothing().when(dishHandler).saveDish(dishRequestDto, token);

        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishRequestDto)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Employee")
    void saveDish_ApplicationException() throws Exception {
        DishRequestDto dishRequestDto = new DishRequestDto();
        String errorMessage = "Información invalida del plato";
        doThrow(new ApplicationException(errorMessage)).when(dishHandler).saveDish(dishRequestDto, token);

        ResultActions response = mockMvc.perform(post("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishRequestDto)));

        response.andDo(print())
                        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Propietario")
    void updateDish_Success() throws Exception {
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto(1L, 1000, "plato de pasta");
        doNothing().when(dishHandler).updateDish(dishUpdateRequestDto, token);

        ResultActions response = mockMvc.perform(put("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishUpdateRequestDto)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Employee")
    void updateDish_ApplicationException() throws Exception {
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto();
        String errorMessage = "Información invalida del plato";
        doThrow(new ApplicationException(errorMessage)).when(dishHandler).updateDish(dishUpdateRequestDto, token);

        ResultActions response = mockMvc.perform(put("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishUpdateRequestDto)));

        response.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Employee")
    void findDishById_Success() throws Exception {
        Long dishId = 1L;
        DishResponseDto expectedResponseDto = new DishResponseDto();
        when(dishHandler.findDishById(dishId)).thenReturn(expectedResponseDto);

        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/dish/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findDishById_ApplicationException() throws Exception {
        Long dishId = 1L;
        String errorMessage = "Plato no encontrado";
        DishResponseDto expectedResponseDto = new DishResponseDto();
        expectedResponseDto.setName(errorMessage);
        ResponseEntity<DishResponseDto> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(expectedResponseDto);
        when(dishHandler.findDishById(dishId)).thenThrow(new ApplicationException(errorMessage));

        ResultActions response = mockMvc.perform(get("/api/v1/plazoleta/dish/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Propietario")
    void enableDisableDish_Success() throws Exception {
        DishEnableDisableRequestDto dishEnableDisableRequestDto = new DishEnableDisableRequestDto(1L);
        doNothing().when(dishHandler).enableDisableDish(dishEnableDisableRequestDto, token);

        ResultActions response = mockMvc.perform(put("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishEnableDisableRequestDto)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Robinson", password = "S3cr3t", authorities = "Employee")
    void enableDisableDish_ApplicationException() throws Exception {
        DishEnableDisableRequestDto dishEnableDisableRequestDto = new DishEnableDisableRequestDto();
        String errorMessage = "Información invalida del plato";
        doThrow(new ApplicationException(errorMessage)).when(dishHandler).enableDisableDish(dishEnableDisableRequestDto, token);

        ResultActions response = mockMvc.perform(put("/api/v1/plazoleta/dish/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishEnableDisableRequestDto)));

        response.andDo(print())
                .andExpect(status().isForbidden());
    }
}