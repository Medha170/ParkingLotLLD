package com.example;

import java.util.List;
import java.util.Map;

public class ParkingLot {
    Map<Type, Double> hourlyRates;
    List<EntryGate> entryGates;
    List<ParkingSpot> parkingSpots;
    List<ExitGate> exits;

    private ParkingLot(ParkingLotBuilder builder) {
        this.hourlyRates = builder.hourlyRates;
        this.entryGates = builder.entryGates;
        this.parkingSpots = builder.parkingSpots;
        this.exits = builder.exits;
    }

    public static ParkingLotBuilder newBuilder() {
        return new ParkingLotBuilder();
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }

    public List<ExitGate> getExits() {
        return exits;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public Map<Type, Double> getHourlyRates() {
        return hourlyRates;
    }

    public static class ParkingLotBuilder {
        private Map<Type, Double> hourlyRates;
        private List<EntryGate> entryGates;
        private List<ParkingSpot> parkingSpots;
        private List<ExitGate> exits;

        public ParkingLotBuilder setHourlyRates(Map<Type, Double> hourlyRates) {
            this.hourlyRates = hourlyRates;
            return this;
        }

        public ParkingLotBuilder setEntry(List<EntryGate> entryGates) {
            this.entryGates = entryGates;
            return this;
        }

        public ParkingLotBuilder setParkingSpots(List<ParkingSpot> parkingSpots) {
            this.parkingSpots = parkingSpots;
            return this;
        }

        public ParkingLotBuilder setExits(List<ExitGate> exits) {
            this.exits = exits;
            return this;
        }

        public ParkingLot build() {
            return new ParkingLot(this);
        }
    }
}
