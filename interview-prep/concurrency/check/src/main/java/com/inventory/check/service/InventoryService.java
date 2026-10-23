package com.inventory.check.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Service;

import com.inventory.check.entity.InventoryEntity;
import com.inventory.check.model.Items;
import com.inventory.check.repository.InventoryRepository;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void buy(Items item, CountDownLatch readLatch, CountDownLatch writeLatch) throws InterruptedException {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemName(item.getItemName());
        readLatch.countDown();
        if (inventoryEntity.getStock() > 0) {
            Thread.sleep(2000);
            writeLatch.await();
            inventoryEntity.setStock(inventoryEntity.getStock() - 1);
            inventoryRepository.save(inventoryEntity);
            System.out.println("Bought Product");
        } else {
            System.out.println("Out of stock");
        }
    }
}
