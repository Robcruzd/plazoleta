package com.pragma.plazoleta.infrastructue.security.jwt;

public interface IExtractAndValidateToken {

    Long extract(String token);
}
