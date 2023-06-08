package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.domain.api.IOrderDishesServicePort;
import com.pragma.plazoleta.domain.spi.IOrderDishesPersistencePort;
import com.pragma.plazoleta.domain.usecase.OrderDishesUseCase;
import com.pragma.plazoleta.infrastructue.out.jpa.adapter.OrderDishesJpaAdapter;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderDishesEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderDishesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanOrderDishesConfiguration {

    private final IOrderDishesRepository orderDishesRepository;
    private final IOrderDishesEntityMapper orderDishesEntityMapper;

    @Bean
    public IOrderDishesPersistencePort orderDishesPersistencePort(){
        return new OrderDishesJpaAdapter(orderDishesRepository, orderDishesEntityMapper);
    }

    @Bean
    public IOrderDishesServicePort orderDishesServicePort() {
        return new OrderDishesUseCase(orderDishesPersistencePort());
    }
}
