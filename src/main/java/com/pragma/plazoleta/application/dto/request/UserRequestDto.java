package com.pragma.plazoleta.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String name;
    private String lastName;
    private String email;
    private Long identityDocument;
    private String cellPhone;
    private String password;
}