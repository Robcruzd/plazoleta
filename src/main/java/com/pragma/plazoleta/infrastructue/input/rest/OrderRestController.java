package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plazoleta/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Agregar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order user added successfuly", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to add user", content = @Content),
            @ApiResponse(responseCode = "403", description = "User no authorized", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> saveOrder(@RequestHeader("Authorization") String token, @RequestBody OrderRequestDto orderRequestDto) {
        try {
            orderHandler.saveOrder(orderRequestDto, token);
            return ResponseEntity.ok("Pedido creado exitosamente");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Listar Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuario no autorizado", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<List<OrderResponseDto>> findRestaurantLists(@RequestHeader("Authorization") String token, @RequestParam("status") int status,
                                                                      @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return new ResponseEntity<>(orderHandler.listOrders(token, status, page, size), HttpStatus.OK);
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
