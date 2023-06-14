package com.pragma.plazoleta.infrastructue.out.feign.users.client;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users-service", url = "localhost:8081/api/v1")
public interface IUsersClient {
    @GetMapping("/users/role/{userId}")
    RoleUserDto getUserById(@PathVariable Long userId);

    @PostMapping("/users/employee")
    ResponseEntity<Long> saveUserEmployee(@RequestHeader("Authorization") String token,
                                          @RequestBody UserRequestDto userRequestDto);
}