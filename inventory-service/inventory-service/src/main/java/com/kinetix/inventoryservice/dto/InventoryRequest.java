package com.kinetix.inventoryservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {

    private String eventId;

    private String customer;

    private Double amount;

}
