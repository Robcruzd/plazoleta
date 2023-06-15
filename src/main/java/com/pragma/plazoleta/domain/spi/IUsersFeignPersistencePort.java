package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import com.pragma.plazoleta.application.dto.response.UserResponseDto;

public interface IUsersFeignPersistencePort {

    RoleUserDto getRoleUserById(Long userId);

    UserRequestDto getUserById(Long userId, String token);

    Long saveUserEmployee(String token, UserRequestDto userRequestDto);
}
