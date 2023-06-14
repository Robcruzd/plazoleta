package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.plazoleta.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.domain.usecase.RestaurantEmployeeUseCase;
import com.pragma.plazoleta.infrastructue.out.jpa.adapter.RestaurantEmployeeJpaAdapter;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEmployeeMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanRestaurantEmployeeConfiguration {

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeMapper restaurantEmployeeMapper;
    private final IUsersFeignPersistencePort usersFeignPersistencePort;
    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeMapper);
    }

    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), usersFeignPersistencePort);
    }
}
