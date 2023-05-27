package com.pragma.plazoleta.infrastructue.out.users.feign;

import com.pragma.plazoleta.application.dto.response.RoleUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface IUsuariosClient {
    @GetMapping("/users/role/{userId}")
    RoleUserDto getUserById(@PathVariable Long userId) ;
}