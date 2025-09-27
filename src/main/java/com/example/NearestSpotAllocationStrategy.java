package com.example;

import java.util.Optional;
import java.util.TreeSet;

public class NearestSpotAllocationStrategy implements ISpotAllocationStrategy {
    private EntryGate entryGate;
    private final DistanceRepository distanceRepository;

    public NearestSpotAllocationStrategy(EntryGate entryGate, DistanceRepository distanceRepository) {
        this.entryGate = entryGate;
        this.distanceRepository = distanceRepository;
    }

    //  manhattan distance between every entry gate and every parkingSpot
    //  stored in Map<EntryGate, TreeSet<parkingSpotDistance>> : Distance repository
    //  get the nearest spot from distance repository and check if it is available
    //  check if the vehicle.type <= parking spot.type 
    //  check if the vehicle required services are supported by the parking spot
    //  if all conditions are met, return the nearest spot

    @Override
    public Optional<ParkingSpot> findSpot(Vehicle vehicle) {
        TreeSet<ParkingSpotDistance> sortedSpots = distanceRepository.getDistances(this.entryGate);

        if (sortedSpots.isEmpty()) {
            return Optional.empty();
        }

        for (ParkingSpotDistance spotDistance : sortedSpots) {
            ParkingSpot spot = spotDistance.getParkingSpot();

            boolean isAvailable = spot.getAvailability() == SpotAvailability.AVAILABLE;
            
            boolean typeMatches = vehicle.getType().ordinal() <= spot.getType().ordinal();
            
            boolean servicesMatch = spot.getSupportedServices().containsAll(vehicle.getRequiredServices());

            if (isAvailable && typeMatches && servicesMatch) {
                return Optional.of(spot);
            }
        }
        
        return Optional.empty();
    }
    
}
