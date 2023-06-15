package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.domain.spi.IStatusOrderPersistencePort;
import com.pragma.plazoleta.infrastructue.out.jpa.adapter.StatusOrderJpaAdapter;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IStatusOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanStatusConfiguration {

    private final IStatusOrderRepository statusOrderRepository;
    private final IStatusOrderEntityMapper statusOrderEntityMapper;
    @Bean
    public IStatusOrderPersistencePort statusOrderPersistencePort() {
        return new StatusOrderJpaAdapter(statusOrderRepository, statusOrderEntityMapper);
    }
}
