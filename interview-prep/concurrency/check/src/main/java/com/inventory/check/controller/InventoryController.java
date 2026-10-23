package com.inventory.check.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.check.model.Items;
import com.inventory.check.service.InventoryService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping ("/inventory")
public class InventoryController {
    private InventoryService inventoryService;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
 
    @PostMapping("/buy")
    public String buy(@RequestBody Items item) throws InterruptedException, ExecutionException {
        CountDownLatch countDownLatchRead = new CountDownLatch(2);
        CountDownLatch countDownLatchWrite = new CountDownLatch(1);
        
        Future<?> exec1 = executorService.submit(
            () -> {
                try {
                    inventoryService.buy(item, countDownLatchRead, countDownLatchWrite);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        );

        Future<?> exec2 = executorService.submit(
            () -> {
                try {
                    inventoryService.buy(item, countDownLatchRead, countDownLatchWrite);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        );
        countDownLatchRead.await();
        countDownLatchWrite.countDown();

        exec1.get();
        exec2.get();
        executorService.shutdown();
        return "Processed";
    }
    
    
}
