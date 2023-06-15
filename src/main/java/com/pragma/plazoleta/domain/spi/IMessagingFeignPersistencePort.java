package com.pragma.plazoleta.domain.spi;

public interface IMessagingFeignPersistencePort {

    void sendSms(String phoneNumber, String message);
}
