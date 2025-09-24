package com.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class PerHourPricingStrategy implements IPricingStrategy {
    private final Map<Type, Double> hourlyRates;

    public PerHourPricingStrategy(Map<Type, Double> hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    @Override
    public double calculatePrice(Ticket ticket, LocalDateTime exitTime) {
        LocalDateTime entryTime = ticket.getEntryTime();
        long hoursParked = Duration.between(entryTime, exitTime).toHours();
        if (hoursParked == 0) {
            hoursParked = 1;
        }

        Type spotType = ticket.getParkingSpot().getType();
        double rate = hourlyRates.getOrDefault(spotType, 20.0); 
        return hoursParked * rate;
    }
}
