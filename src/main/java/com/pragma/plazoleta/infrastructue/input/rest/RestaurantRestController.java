package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plazoleta/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Agregar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> saveRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        try {
            restaurantHandler.saveRestaurant(restaurantRequestDto);
            return ResponseEntity.ok("Restaurante creado exitosamente");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener restaurante por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantRequestDto> findRestaurantById(@PathVariable Long restaurantId) {
        try {
            RestaurantRequestDto restaurantRequestDto = restaurantHandler.findRestaurantById(restaurantId);
            return new ResponseEntity<>(restaurantRequestDto, HttpStatus.OK);
        } catch (ApplicationException e) {
            RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
            restaurantRequestDto.setName(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restaurantRequestDto);
        }
    }

    @Operation(summary = "Listar restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurantes encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<List<RestaurantListResponseDto>> findRestaurantLists(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return new ResponseEntity<>(restaurantHandler.listRestaurants(page, size), HttpStatus.OK);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
