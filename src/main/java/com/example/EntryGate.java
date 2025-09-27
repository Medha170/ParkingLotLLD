package com.example;

import java.util.Optional;

public class EntryGate {
    private final int gateNumber;
    private final ISpotAllocationStrategy spotAllocationStrategy;
    private Vehicle vehicle;

    public EntryGate(int gateNumber, ISpotAllocationStrategy spotAllocationStrategy) {
        this.gateNumber = gateNumber;
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    public Ticket generateTicket() {
        Ticket ticket = new Ticket(vehicle);
        return ticket;
    }
    
    public Ticket processEntry() {
        Ticket ticket = generateTicket();

        Optional<ParkingSpot> foundSpot = spotAllocationStrategy.findSpot(vehicle);
        
        if (foundSpot.isPresent()) {
            ParkingSpot parkingSpot = foundSpot.get();
            
            ticket.setParkingSpot(parkingSpot);
            
            parkingSpot.setAvailability(SpotAvailability.OCCUPIED);

            System.out.println("Ticket generated for vehicle of type " + vehicle.getType());
            System.out.println("Assigned to parking spot: " + parkingSpot.getSpotId());
            
            return ticket;
        } else {
            System.out.println("No suitable parking spot found. Vehicle entry denied.");
            return null;
        }
    }
    
    public int getGateNumber() {
        return gateNumber;
    }
}