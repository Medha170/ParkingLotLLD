package com.example;

import java.util.Optional;

public interface ISpotAllocationStrategy {
    Optional<ParkingSpot> findSpot(Vehicle vehicle);
}
