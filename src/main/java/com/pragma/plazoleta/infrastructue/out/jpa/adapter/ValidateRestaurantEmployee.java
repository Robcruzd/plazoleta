package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.plazoleta.application.handler.IValidateRestaurantEmployee;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEmployeeMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantEmployeeRepository;
import com.pragma.plazoleta.infrastructue.security.jwt.IExtractAndValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateRestaurantEmployee implements IValidateRestaurantEmployee {

    private final IExtractAndValidateToken extractAndValidateToken;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeMapper restaurantEmployeeMapper;

    @Override
    public RestaurantEmployeeRequestDto validate(String token) {
        Long employeeId = extractAndValidateToken.extract(token);
        RestaurantEmployeeEntity restaurantEmployeeEntity = restaurantEmployeeRepository.findByEmployeeId(employeeId).orElse(null);
        if(restaurantEmployeeEntity != null) {
            return restaurantEmployeeMapper.toDto(restaurantEmployeeEntity);
        } else
            throw new RequestException("The employee doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
