package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderDeliverRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
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
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order user added successfuly", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to add user", content = @Content),
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

    @Operation(summary = "List orders")
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

    @Operation(summary = "Update orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders updated successfuly", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update orders", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<String> updateOrders(@RequestHeader("Authorization") String token, @RequestBody List<UpdateOrderRequestDto> updateOrderRequestDto) {
        try {
            orderHandler.updateOrders(updateOrderRequestDto, token);
            return ResponseEntity.ok("Orders updated succesfully");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update order ready")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfuly", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update order", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content)
    })
    @PutMapping("/ready")
    public ResponseEntity<String> updateOrderReady(@RequestHeader("Authorization") String token, @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        try {
            orderHandler.updateOrderReady(updateOrderRequestDto, token);
            return ResponseEntity.ok("Order ready updated succesfully");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update order deliver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfuly", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update order", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content)
    })
    @PutMapping("/deliver")
    public ResponseEntity<String> updateOrderDeliver(@RequestHeader("Authorization") String token, @RequestBody UpdateOrderDeliverRequestDto updateOrderDeliverRequestDto) {
        try {
            orderHandler.updateOrderDeliver(updateOrderDeliverRequestDto, token);
            return ResponseEntity.ok("Order deliver updated succesfully");
        } catch (ApplicationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
