package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
import com.pragma.plazoleta.domain.model.StatusOrderModel;
import com.pragma.plazoleta.domain.spi.IMessagingFeignPersistencePort;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.domain.spi.IStatusOrderPersistencePort;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;

public class OrderUseCase implements IOrderServicePort {
    private static final int PIN_LENGTH = 6;
    private final IOrderPersistencePort orderPersistencePort;
    private final IStatusOrderPersistencePort statusOrderPersistencePort;
    private final IMessagingFeignPersistencePort messagingFeignPersistencePort;
    private final IUsersFeignPersistencePort usersFeignPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IStatusOrderPersistencePort statusOrderPersistencePort, IMessagingFeignPersistencePort messagingFeignPersistencePort, IUsersFeignPersistencePort usersFeignPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.statusOrderPersistencePort = statusOrderPersistencePort;
        this.messagingFeignPersistencePort = messagingFeignPersistencePort;
        this.usersFeignPersistencePort = usersFeignPersistencePort;
    }

    @Override
    public Long saveOrder(Long customerId, Long restaurantId) {
        return orderPersistencePort.saveOrder(customerId, restaurantId);
    }

    @Override
    public List<OrderModel> listOrders(Long restaurantId, int status, int page, int size) {
        return orderPersistencePort.listOrders(restaurantId, status, page, size);
    }

    @Override
    public void updateOrders(Long userId, List<OrderModel> orderModelList) {
        int statusId = 2;
        orderPersistencePort.updateOrder(userId, orderModelList, statusId);
    }

    @Override
    public void updateOrderReady(OrderModel orderModel, String token) {
        int statusId = 3;
        StatusOrderModel statusOrderModel = statusOrderPersistencePort.findStatusOrderById(statusId);
        UserRequestDto userRequestDto = usersFeignPersistencePort.getUserById(orderModel.getCustomerId(), token);
        String securityPin = generatePin();
        orderModel.setStatus(statusOrderModel);
        orderModel.setSecurityPin(securityPin);
        orderPersistencePort.updateOrderReady(orderModel);
        String message = "Su pin de seguridad es: " + securityPin;
        messagingFeignPersistencePort.sendSms(userRequestDto.getCellPhone(), message);
    }

    @Override
    public void updateOrderDeliver(OrderModel orderModel, String token) {
        if(orderModel.getStatus().getId() != 3 && orderModel.getStatus().getId() != 4)
            throw new RequestException("status not allowed", HttpStatus.BAD_REQUEST);
        int statusId = 4;
        if(orderModel.getStatus().getId() == 4) // If the status is delivered, it change to ready
            statusId = 3;
        StatusOrderModel statusOrderModel = statusOrderPersistencePort.findStatusOrderById(statusId);
        orderModel.setStatus(statusOrderModel);
        orderModel.setSecurityPin("delivered");
        orderPersistencePort.updateOrderReady(orderModel);
    }

    public static String generatePin() {
        StringBuilder pinBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < PIN_LENGTH; i++) {
            int randomIndex = random.nextInt(36);
            char randomChar = (char) (randomIndex < 10 ? '0' + randomIndex : 'A' + randomIndex - 10);
            pinBuilder.append(randomChar);
        }

        return pinBuilder.toString();
    }
}
