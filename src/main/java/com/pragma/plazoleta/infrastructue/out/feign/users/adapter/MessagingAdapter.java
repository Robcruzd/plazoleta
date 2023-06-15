package com.pragma.plazoleta.infrastructue.out.feign.users.adapter;

import com.pragma.plazoleta.domain.spi.IMessagingFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.feign.users.client.IMessagingClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingAdapter implements IMessagingFeignPersistencePort {

    private final IMessagingClient messagingClient;
    @Override
    public void sendSms(String phoneNumber, String message) {
        try {
            messagingClient.sendSms(phoneNumber, message);
        } catch (FeignException.BadRequest e) {
            throw new RequestException("Message not sent for bad request", HttpStatus.BAD_REQUEST);
        } catch (FeignException e) {
            throw new RequestException("Message not sent", HttpStatus.valueOf(e.status()));
        }
    }
}
