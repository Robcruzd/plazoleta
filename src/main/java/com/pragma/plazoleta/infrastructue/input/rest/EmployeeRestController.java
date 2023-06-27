package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeFullRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.impl.RestaurantEmployeeHandler;
import com.pragma.plazoleta.infrastructue.out.ssmParameters.client.ISsmClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plazoleta")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final RestaurantEmployeeHandler restaurantEmployeeHandler;
    private final ISsmClient ssmClient;

    @Operation(summary = "Create restaurant employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant employee created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error in the request", content = @Content)
    })
    @PostMapping("/restaurant_employee")
    public ResponseEntity<String> saveUserEmployee(@RequestHeader("Authorization") String token,
                                 @RequestBody RestaurantEmployeeFullRequestDto restaurantEmployeeFullRequestDto) {
        try {
            restaurantEmployeeHandler.saveRestaurantEmployee(restaurantEmployeeFullRequestDto, token);
            return ResponseEntity.status(HttpStatus.CREATED).body(ssmClient.getParameter("/plazoleta/controller/employee/saveUserEmployee"));
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
