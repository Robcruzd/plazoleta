package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurantModel));
        restaurantEntityMapper.toModel(restaurantEntity);
    }

    @Override
    public RestaurantModel findRestaurantById(Long restaurantId) throws RequestException {
        try {
            RestaurantEntity restaurantEntity = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new RequestException("Restaurante no encontrado", HttpStatus.NOT_FOUND));
            return this.restaurantEntityMapper.toModel(restaurantEntity);
        } catch (RequestException e) {
            throw new RequestException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
