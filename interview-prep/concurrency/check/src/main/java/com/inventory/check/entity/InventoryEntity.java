package com.inventory.check.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class InventoryEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String itemName;
    private Integer stock;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String item) {
        this.itemName = item;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    
}
