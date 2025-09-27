package com.example;

import java.util.List;

public class ParkingSpot {
    private final int floorNo;
    private final int spotNo;
    private final Type type;
    private SpotAvailability availability;
    private List<IParkingService> supportedServices;

    public ParkingSpot(int floorNo, int spotNo, Type type) {
        this.floorNo = floorNo;
        this.spotNo = spotNo;
        this.type = type;
        this.availability = SpotAvailability.AVAILABLE; 
    }

    public String getSpotId() {
        return "F" + floorNo + "-S" + spotNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public Type getType() {
        return type;
    }

    public SpotAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(SpotAvailability availability) {
        this.availability = availability;
    }

    public List<IParkingService> getSupportedServices() {
        return supportedServices;
    }

    public void addService(IParkingService service) {
        this.supportedServices.add(service);
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id='" + getSpotId() + '\'' +
                ", type=" + type +
                ", availability=" + availability +
                '}';
    }
}
