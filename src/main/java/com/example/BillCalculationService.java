package com.example;

import java.time.LocalDateTime;
import java.util.List;

public class BillCalculationService {
    private final IPricingStrategy pricingStrategy;

    public BillCalculationService(IPricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculateAmount(Ticket ticket, LocalDateTime exitTime) {
        double totalAmount = pricingStrategy.calculatePrice(ticket, exitTime);
        Vehicle vehicle = ticket.getVehicle();
        List<IParkingService> requiredServices = vehicle.getRequiredServices();
        for (int i = 0; i < requiredServices.size(); i++) {
            requiredServices.get(i).activateService(vehicle.getServicesConfig().get(i));
        }
        return totalAmount;
    }
}
