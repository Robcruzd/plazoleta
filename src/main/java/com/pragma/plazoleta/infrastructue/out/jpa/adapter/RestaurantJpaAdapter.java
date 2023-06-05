package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;

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

    @Override
    public List<RestaurantModel> findAllRestaurants(int page, int size) {
        try {
            Sort sort  = Sort.by("name").ascending();
            Pageable pageable = PageRequest.of(page-1, size, sort);
            Page<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(pageable);
            return this.restaurantEntityMapper.toRestaurantsModel(restaurantEntities);
        } catch (RequestException e) {
            throw new RequestException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
