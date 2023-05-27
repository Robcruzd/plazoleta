package com.pragma.plazoleta.infrastructue.configuration;

import com.pragma.plazoleta.infrastructue.out.users.feign.IUsuariosClient;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFeignConfiguration {

    @Bean
    public IUsuariosClient usuariosClient() {
        Feign.Builder builder = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract(new SpringMvcContract());
        return builder.target(IUsuariosClient.class, "http://localhost:8081/api/v1");
    }
}