package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DistanceRepository {
    private final Map<EntryGate, TreeSet<ParkingSpotDistance>> distances;

    public DistanceRepository(List<EntryGate> entryGates, List<ParkingSpot> parkingSpots) {
        Map<EntryGate, TreeSet<ParkingSpotDistance>> distances = new HashMap<>();

        for (EntryGate entryGate : entryGates) {
            TreeSet<ParkingSpotDistance> sortedSpots = new TreeSet<>();
            for (ParkingSpot spot : parkingSpots) {

                double manhattanDistance = calculateManhattanDistance(
                        entryGate.getGateNumber(), 0,
                        spot.getSpotNo(), spot.getFloorNo()
                );

                sortedSpots.add(new ParkingSpotDistance(spot, manhattanDistance));
            }

            distances.put(entryGate, sortedSpots);
        }

        this.distances = distances;
    }

    public TreeSet<ParkingSpotDistance> getDistances(EntryGate entryGate) {
        return distances.get(entryGate);
    }

    private double calculateManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
