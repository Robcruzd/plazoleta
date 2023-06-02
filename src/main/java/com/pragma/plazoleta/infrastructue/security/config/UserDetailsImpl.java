package com.pragma.plazoleta.infrastructue.security.config;

import com.pragma.plazoleta.infrastructue.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private final JwtService jwtService;
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String email = jwtService.extractUserName(token);
        List<SimpleGrantedAuthority> authorities = jwtService.extractUserRole(token);
        return new org.springframework.security.core.userdetails.User(email,email,
                authorities);
    }
}
