package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.domain.usecase.OrderUseCase;
import com.pragma.plazoleta.infrastructue.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanOrderConfiguration {

    private final IOrderRepository orderRepository;
    private final IStatusOrderRepository statusOrderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, statusOrderRepository, orderEntityMapper);
    }
    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort());
    }
}
