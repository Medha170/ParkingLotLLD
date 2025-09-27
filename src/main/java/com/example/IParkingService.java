package com.example;

import java.util.Map;

public interface IParkingService {
    void assignSpot(ParkingSpot parkingSpot);
    void vacateSpot(ParkingSpot parkingSpot);
    void putUnderMaintenance(ParkingSpot parkingSpot);
    double activateService(Map<String, Object> config);
}
