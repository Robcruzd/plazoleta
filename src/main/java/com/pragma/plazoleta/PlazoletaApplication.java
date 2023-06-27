package com.pragma.plazoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

@SpringBootApplication
@EnableFeignClients
//@EnableAutoConfiguration
//@EnableContextInstanceData
public class PlazoletaApplication {

	public static void main(String[] args) {
		// Get args
//		String paraName = args[0];

		Region region = Region.US_EAST_1;
		SsmClient ssmClient = SsmClient.builder()
				.region(region)
				.build();

		try {
			GetParameterRequest parameterRequest = GetParameterRequest.builder()
					.name("/plazoleta/database/url")
					.build();

			GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
			System.out.println("The parameter value is "+parameterResponse.parameter().value());

		} catch (SsmException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		SpringApplication.run(PlazoletaApplication.class, args);
	}
}
