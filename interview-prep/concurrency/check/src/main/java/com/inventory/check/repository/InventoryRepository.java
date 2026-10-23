package com.inventory.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.check.entity.InventoryEntity;

@Repository 
public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer>{
    public InventoryEntity findByItemName(String itemName);
}
