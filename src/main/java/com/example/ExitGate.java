package com.example;

import java.util.Map;

public class ExitGate {
    private int gateNumber;
    private BillCalculationService billCalculationService;
    private Ticket ticket;

    public ExitGate(int gateNumber, Ticket ticket, Map<Type, Double> hourlyRates) {
        this.gateNumber = gateNumber;
        this.billCalculationService = new BillCalculationService(new PerHourPricingStrategy(hourlyRates));
        this.ticket = ticket;
    }

    public Bill generateBill() {
        Bill bill = new Bill();
        double totalAmount = billCalculationService.calculateAmount(ticket, bill.getExitTime());
        bill.setTotalAmount(totalAmount);
        return bill;
    }

    public void processExit() {
        // Generate the bill
        Bill bill = generateBill();
        System.out.println("Processing exit for ticket ID: " + ticket.getId());
        System.out.println("Total Amount Due: " + bill.getTotalAmount());

        // Update the parking spot's availability
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        if (parkingSpot != null) {
            parkingSpot.setAvailability(SpotAvailability.AVAILABLE);
            System.out.println("Parking spot " + parkingSpot.getSpotId() + " is now available.");
        }
    }
    
}
