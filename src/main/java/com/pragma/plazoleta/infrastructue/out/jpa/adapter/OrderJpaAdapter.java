package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IStatusOrderRepository statusOrderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    @Override
    public Long saveOrder(Long customerId, Long restaurantId) {
        int statusOrderId = 1;
        StatusOrderEntity statusOrderEntity = statusOrderRepository.findById(statusOrderId).orElseThrow(
                ()->new RequestException("Estado no encontrado", HttpStatus.NOT_FOUND)
        );
        OrderEntity orderEntity = new OrderEntity(null, customerId, LocalDate.now(), statusOrderEntity, null, restaurantId);
        return orderRepository.save(orderEntity).getId();
    }

    @Override
    public List<OrderModel> listOrders(Long restaurantId, int status, int page, int size) {
        try {
            Sort sort  = Sort.by("date").ascending();
            Pageable pageable = PageRequest.of(page-1, size, sort);
            Page<OrderEntity> orderEntities = orderRepository.findAllByStatusIdAndRestaurantId(status, restaurantId, pageable);
            return orderEntityMapper.toModelList(orderEntities);
        } catch (RequestException e) {
            throw new RequestException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updateOrder(Long userId, List<OrderModel> orderModelList, int statusId) {
        try {
            StatusOrderEntity statusOrderEntity = statusOrderRepository.findById(statusId).orElseThrow(
                    ()->new RequestException("Status not found", HttpStatus.NOT_FOUND)
            );
            List<OrderEntity> orderEntityList = orderEntityMapper.toEntityList(orderModelList);
            orderEntityList.forEach(orderEntity -> {
                orderEntity.setEmployeeId(userId);
                orderEntity.setStatus(statusOrderEntity);
            });
            orderRepository.saveAll(new ArrayList<>(orderEntityList));
        } catch (RequestException e) {
            throw new RequestException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
