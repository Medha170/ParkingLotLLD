package com.example;

public class Entry implements IGate{
    private int gateNumber;
    private Ticket ticket;
    private Vehicle vehicle;

    public Entry(int gateNumber) {
        this.gateNumber = gateNumber;
    }

    @Override
    public void generate() {
        ticket = new Ticket(vehicle);
    }
    
    public Ticket getTicket() {
        return ticket;
    }
}
