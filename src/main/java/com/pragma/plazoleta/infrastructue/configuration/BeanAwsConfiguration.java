package com.pragma.plazoleta.infrastructue.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.SsmClientBuilder;

@Configuration
public class BeanAwsConfiguration {

    @Bean
    public SsmClient ssmClient() {
        SsmClientBuilder builder = SsmClient.builder();
        builder.credentialsProvider(DefaultCredentialsProvider.builder().build());
        return builder.build();
    }
}
