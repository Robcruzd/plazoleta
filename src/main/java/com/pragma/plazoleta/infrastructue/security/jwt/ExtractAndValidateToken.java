package com.pragma.plazoleta.infrastructue.security.jwt;

import com.pragma.plazoleta.infrastructue.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractAndValidateToken implements IExtractAndValidateToken{

    private final JwtService jwtService;
    @Override
    public Long extract(String token){
        String jwt = jwtService.parseJwt(token);
        if (!jwtService.isValidToken(jwt))
            throw new RequestException("Token invalido", HttpStatus.FORBIDDEN);

        return  jwtService.extractUserId(jwt);
    }

}
