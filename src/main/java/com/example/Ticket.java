package com.example;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String id;
    private ParkingSpot parkingSpot;
    private final LocalDateTime entryTime;
    private final Vehicle vehicle;

    public Ticket(Vehicle vehicle) {
        this.id = UUID.randomUUID().toString();
        this.parkingSpot = null;
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
