package com.example;

import java.time.LocalDateTime;

public interface IPricingStrategy {
    double calculatePrice(Ticket ticket, LocalDateTime exitTime);
}
