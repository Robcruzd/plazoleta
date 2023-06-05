package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.users.feign.IUsuariosClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUsuariosClient usuariosClient;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, usuariosClient);
    }

    @Test
    void saveRestaurant_Success() throws DomainException {
        Long ownerId = 1L;
        RestaurantModel restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", ownerId);

        RoleUserDto owner = new RoleUserDto(1L, "Propietario");

        when(usuariosClient.getUserById(restaurantModel.getOwnerId())).thenReturn(owner);

        restaurantUseCase.saveRestaurant(restaurantModel);

        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurantModel);
    }

    @Test
    void saveRestaurant_DomainExceptionThrown() {
        RestaurantModel restaurantModel = new RestaurantModel();

        Assertions.assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurantModel);
    }

    @Test
    void findRestaurantById() {
        Long restaurantId = 1L;
        RestaurantModel restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);

        when(restaurantPersistencePort.findRestaurantById(restaurantId)).thenReturn(restaurantModel);

        RestaurantModel expectedRestaurantModel = restaurantUseCase.findRestaurantById(restaurantId);

        assertAll("Restaurante obtenido",
                () -> assertEquals(restaurantModel.getId(), expectedRestaurantModel.getId()),
                () -> assertEquals(restaurantModel.getName(), expectedRestaurantModel.getName()),
                () -> assertEquals(restaurantModel.getNit(), expectedRestaurantModel.getNit()),
                () -> assertEquals(restaurantModel.getOwnerId(), expectedRestaurantModel.getOwnerId()),
                () -> assertEquals(restaurantModel.getAddress(), expectedRestaurantModel.getAddress()),
                () -> assertEquals(restaurantModel.getPhone(), expectedRestaurantModel.getPhone()),
                () -> assertEquals(restaurantModel.getUrlLogo(), expectedRestaurantModel.getUrlLogo()));
    }

    @Test
    void findRestaurantById_DomainExceptionThrown() {
        Long restaurantId = 1L;
        String errorMessage = "Restaurante no encontrado";

        when(restaurantPersistencePort.findRestaurantById(restaurantId)).thenThrow(new RequestException(errorMessage, HttpStatus.NOT_FOUND));

        Assertions.assertThrows(DomainException.class, () -> restaurantUseCase.findRestaurantById(restaurantId));
        verify(restaurantPersistencePort, times(1)).findRestaurantById(restaurantId);
    }

    @Test
    void testFindAllRestaurants() {
        int page = 1;
        int size = 10;

        List<RestaurantModel> expectedRestaurantModels = Arrays.asList(
                new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L),
                new RestaurantModel(2L, "Restaurante2", 123456L, "calle 123", "09876543", "https://urlLogo", 1L)
        );

        when(restaurantPersistencePort.findAllRestaurants(page, size)).thenReturn(expectedRestaurantModels);

        List<RestaurantModel> actualRestaurantModels = restaurantUseCase.findAllRestaurants(page, size);

        assertEquals(expectedRestaurantModels, actualRestaurantModels);
    }
}