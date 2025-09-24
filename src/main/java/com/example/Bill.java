package com.example;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bill {
    private final String id;
    private final LocalDateTime exitTime;
    private double totalAmount;

    public Bill() {
        this.id = UUID.randomUUID().toString();
        this.exitTime = LocalDateTime.now();
        this.totalAmount = 0.0;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
