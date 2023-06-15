package com.pragma.plazoleta.infrastructue.out.feign.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "messaging-service", url = "localhost:8083/api/v1")
public interface IMessagingClient {
    @GetMapping("/messaging/")
    void sendSms(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("message") String message);
}
