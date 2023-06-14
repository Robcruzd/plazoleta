package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.RoleUserDto;

public interface IUsersFeignPersistencePort {

    RoleUserDto getUserById(Long userId);

    Long saveUserEmployee(String token, UserRequestDto userRequestDto);
}
