package com.example;

import java.util.Map;

public class ElectricService implements IParkingService{

    @Override
    public void assignSpot(ParkingSpot parkingSpot) {
        parkingSpot.setAvailability(SpotAvailability.OCCUPIED);
    }

    @Override
    public void vacateSpot(ParkingSpot parkingSpot) {
        parkingSpot.setAvailability(SpotAvailability.AVAILABLE);
    }

    @Override
    public void putUnderMaintenance(ParkingSpot parkingSpot) {
        parkingSpot.setAvailability(SpotAvailability.MAINTENANCE);
    }

    @Override
    public double activateService(Map<String, Object> config) {
        if (config == null || config.isEmpty()) {
            throw new IllegalArgumentException("Config map cannot be null or empty for ElectricService.");
        }

        String spotId = (String) config.get("spotId");
        Integer duration = (Integer) config.getOrDefault("duration", 1); 
        Boolean fastCharging = (Boolean) config.getOrDefault("fastCharging", false);

        double baseRatePerHour = 5.0; 
        double fastChargingMultiplier = 1.5; 

        double rate = fastCharging ? baseRatePerHour * fastChargingMultiplier : baseRatePerHour;
        double totalCharge = rate * duration;

        System.out.println("Electric service activated for spot " + spotId);
        System.out.println("Duration: " + duration + " hr(s), Fast charging: " + fastCharging);
        System.out.println("Total Charge: $" + totalCharge);

        return totalCharge;
    }
    
}
