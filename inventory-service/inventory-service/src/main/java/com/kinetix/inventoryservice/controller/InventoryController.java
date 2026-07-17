package com.kinetix.inventoryservice.controller;

import com.kinetix.inventoryservice.dto.InventoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody InventoryRequest request) {

        log.info("========================================");
        log.info("Inventory Reservation Request Received");
        log.info("Event Id : {}", request.getEventId());
        log.info("Customer : {}", request.getCustomer());
        log.info("Amount : {}", request.getAmount());
        log.info("Inventory Reserved Successfully");
        log.info("========================================");

        return ResponseEntity.ok("Inventory Reserved Successfully");
    }

}
