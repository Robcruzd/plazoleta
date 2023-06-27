package com.pragma.plazoleta.infrastructue.configuration;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import javax.annotation.PostConstruct;

@Configuration
public class DatabaseConfiguration {

    private final SsmClient ssmClient;

    public DatabaseConfiguration(SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    @PostConstruct
    public void init() {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/plazoleta/database/password")
                .build();

        GetParameterResponse response = ssmClient.getParameter(request);
        String password = response.parameter().value();
        System.out.println("password");
        // Utiliza el valor del parámetro como sea necesario
        // Por ejemplo, configura la contraseña en tu fuente de datos
//        dataSource.setPassword(password);
    }
}
