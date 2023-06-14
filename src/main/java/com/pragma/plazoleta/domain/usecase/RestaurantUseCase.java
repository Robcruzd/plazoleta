package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.feign.users.client.IUsersClient;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUsersFeignPersistencePort usersFeignPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUsersFeignPersistencePort usersFeignPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.usersFeignPersistencePort = usersFeignPersistencePort;
    }

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) throws DomainException {
        try {
            restaurantModel.validate();
            RoleUserDto owner = usersFeignPersistencePort.getUserById(restaurantModel.getOwnerId());
            if (owner.getId() != 1)
                throw new DomainException("El id del usuario ingresado no tiene el rol para realizar esta acción");
            restaurantModel.setOwnerId(owner.getId());
            this.restaurantPersistencePort.saveRestaurant(restaurantModel);
        } catch (Exception e) {
            throw new DomainException(e.getMessage());
        }
    }

    @Override
    public RestaurantModel findRestaurantById(Long restaurantId) throws DomainException {
        try {
            return this.restaurantPersistencePort.findRestaurantById(restaurantId);
        } catch (RequestException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @Override
    public List<RestaurantModel> findAllRestaurants(int page, int size) {
        return restaurantPersistencePort.findAllRestaurants(page, size);
    }
}
