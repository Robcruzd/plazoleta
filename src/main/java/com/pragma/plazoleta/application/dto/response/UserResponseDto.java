package com.pragma.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String lastName;
    private Long identityDocument;
    private String email;
    private String cellPhone;
    private String password;
    private RoleUserDto roleModel;
}
