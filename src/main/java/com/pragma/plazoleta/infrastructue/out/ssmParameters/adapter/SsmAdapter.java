package com.pragma.plazoleta.infrastructue.out.ssmParameters.adapter;

import com.pragma.plazoleta.infrastructue.out.ssmParameters.client.ISsmClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;


@Service
@RequiredArgsConstructor
public class SsmAdapter implements ISsmClient {

    private final SsmClient ssmClient;
    @Override
    public String getParameter(String key) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(key)
                .build();

        GetParameterResponse response = ssmClient.getParameter(request);
        String parameter = response.parameter().value();
        System.out.println(parameter);
        return parameter;
    }
}
