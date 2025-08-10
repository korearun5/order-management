package com.arun.ordermgmt.inventory.service;

import com.arun.ordermgmt.inventory.domain.Inventory;
import com.arun.ordermgmt.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void initializeInventory(String productId, String productName, int quantity) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setProductName(productName);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
    }

    public Inventory getInventory(String productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
    }
}