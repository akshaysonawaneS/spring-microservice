package com.learning.order_service.config;

import com.learning.order_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("api/inventory")
    InventoryResponse[] checkStock(@RequestParam("skuCode") List<String> skuCodes);
}
