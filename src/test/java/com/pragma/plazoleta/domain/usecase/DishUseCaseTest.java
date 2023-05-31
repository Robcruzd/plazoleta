package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    @Test
    void saveDishTest_Success() {
        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", null, null, true);

        dishUseCase.saveDish(dishModel);

        verify(dishPersistencePort).saveDish(dishModel);
    }

    @Test
    void saveDishTest_DomainException() {
        DishModel dishModel = new DishModel();
        dishModel.setName("Plato prueba");
        dishModel.setPrice(3000);

        String errorMessage = "Plato incorrecto";
        when(dishPersistencePort.saveDish(dishModel)).thenThrow(new DomainException(errorMessage));

        assertThrows(DomainException.class, () -> dishUseCase.saveDish(dishModel));
    }

    @Test
    void findDishByIdTest_Success() {
        Long dishId = 1L;
        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", null, null, true);


        when(dishPersistencePort.findDishById(dishId)).thenReturn(dishModel);

        DishModel dishModelReturn = dishUseCase.findDishById(dishId);

        assertEquals(dishModel, dishModelReturn);
    }

    @Test
    void findDishByIdTest_DomainException() {
        Long dishId = 1L;
        String errorMessage = "Plato incorrecto";

        when(dishPersistencePort.findDishById(dishId)).thenThrow(new DomainException(errorMessage));

        assertThrows(DomainException.class, () -> dishUseCase.findDishById(dishId));
    }
}