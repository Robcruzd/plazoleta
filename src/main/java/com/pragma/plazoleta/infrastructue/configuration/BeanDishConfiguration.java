package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.domain.usecase.DishUseCase;
import com.pragma.plazoleta.infrastructue.out.jpa.adapter.DishJpaAdapter;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanDishConfiguration {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final BeanCategoryConfiguration beanCategoryConfiguration;
    private final BeanConfiguration beanConfiguration;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper,
                beanCategoryConfiguration.categoryServicePort(), beanConfiguration.restaurantServicePort(),
                restaurantEntityMapper, categoryEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort());
    }
}
