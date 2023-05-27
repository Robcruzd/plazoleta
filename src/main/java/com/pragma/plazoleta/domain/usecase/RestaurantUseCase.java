package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructue.out.users.feign.IUsuariosClient;
import com.pragma.usuario.domain.exception.DomainException;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUsuariosClient usuariosClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUsuariosClient usuariosClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.usuariosClient = usuariosClient;
    }

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) throws DomainException {
        try {
            restaurantModel.validate();
            RoleUserDto owner = usuariosClient.getUserById(restaurantModel.getOwnerId());
            if (owner.getId() != 1)
                throw new DomainException("El id del usuario ingresado no tiene el rol para realizar esta acción");
            restaurantModel.setOwnerId(owner.getId());
            this.restaurantPersistencePort.saveRestaurant(restaurantModel);
        } catch (Exception e) {
            throw new DomainException(e.getMessage());
        }
    }
}
