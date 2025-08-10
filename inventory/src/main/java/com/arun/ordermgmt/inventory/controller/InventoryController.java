package com.arun.ordermgmt.inventory.controller;

import com.arun.ordermgmt.inventory.domain.Inventory;
import com.arun.ordermgmt.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public void initializeInventory(
            @RequestParam String productId,
            @RequestParam String productName,
            @RequestParam int quantity) {
        inventoryService.initializeInventory(productId, productName, quantity);
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable String productId) {
        return inventoryService.getInventory(productId);
    }
}