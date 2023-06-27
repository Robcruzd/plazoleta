package com.pragma.plazoleta.infrastructue.out.feign.users.adapter;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.feign.users.client.IUsersClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersAdapter implements IUsersFeignPersistencePort {

    private final IUsersClient usersClient;
    @Override
    public RoleUserDto getRoleUserById(Long userId) {
        return usersClient.getRoleUserById(userId);
    }

    @Override
    public UserRequestDto getUserById(Long userId, String token) {
        return usersClient.getUserById(token, userId);
    }

    @Override
    public Long saveUserEmployee(String token, UserRequestDto userRequestDto) {
        try {
            ResponseEntity<Long> response = usersClient.saveUserEmployee(token, userRequestDto);
            return response.getBody();
        } catch (FeignException.BadRequest | IllegalArgumentException e) {
            throw new RequestException("User no created for bad request", HttpStatus.BAD_REQUEST);
        } catch (FeignException e) {
            throw new RequestException("User no created", HttpStatus.BAD_REQUEST);
        }
    }
}
