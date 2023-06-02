package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IDishHandler;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plazoleta/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Agregar plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> saveDish(@RequestHeader("Authorization") String token,
                                           @RequestBody DishRequestDto dishRequestDto) {
        try {
            dishHandler.saveDish(dishRequestDto, token);
            return ResponseEntity.ok("Plato creado exitosamente");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Modificar plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato modicado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<String> updateDish(@RequestHeader("Authorization") String token,
                                             @RequestBody DishUpdateRequestDto dishUpdateRequestDto) {
        try {
            dishHandler.updateDish(dishUpdateRequestDto, token);
            return ResponseEntity.ok("Plato actualizado exitosamente");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
    })
    @GetMapping("/{dishId}")
    public ResponseEntity<DishResponseDto> findDishById(@PathVariable Long dishId) {
        try {
            DishResponseDto dishResponseDto = dishHandler.findDishById(dishId);
            return new ResponseEntity<>(dishResponseDto, HttpStatus.OK);
        } catch (ApplicationException e) {
            DishResponseDto dishResponseDto = new DishResponseDto();
            dishResponseDto.setName(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dishResponseDto);
        }
    }
}
