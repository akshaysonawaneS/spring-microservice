package com.learning.order_service.controller;

import com.learning.order_service.dto.OrderRequest;
import com.learning.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping(value = "/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<String>> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(() -> {
            orderService.placeOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
        });
    }

    public CompletableFuture<ResponseEntity<String>> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
//        log.info("ERROR: " + runtimeException.getMessage(), runtimeException);
        return CompletableFuture.completedFuture(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Inventory service is currently unavailable. Please try again later.")
        );
    }

//    public CompletableFuture<ResponseEntity<String>> fallBackMethod(OrderRequest orderRequest, Throwable throwable){
//        return CompletableFuture.completedFuture(
//                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                        .body("TimeOut Occured")
//        );
//    }
}
