package com.example;

import java.util.Objects;

public class ParkingSpotDistance implements Comparable<ParkingSpotDistance> {
    private final ParkingSpot parkingSpot;
    private final double distance;

    public ParkingSpotDistance(ParkingSpot parkingSpot, double distance) {
        this.parkingSpot = parkingSpot;
        this.distance = distance;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    @Override
    public int compareTo(ParkingSpotDistance other) {
        return Double.compare(this.distance, other.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpotDistance that = (ParkingSpotDistance) o;
        return Objects.equals(parkingSpot, that.parkingSpot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSpot);
    }
    
}
