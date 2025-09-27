package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("============== Setting up Parking Lot ==============");
        
        IParkingService sharedElectricService = new ElectricService();

        // 1. Define Hourly Rates
        Map<Type, Double> hourlyRates = new HashMap<>();
        hourlyRates.put(Type.SMALL, 10.0);
        hourlyRates.put(Type.MEDIUM, 20.0);
        hourlyRates.put(Type.LARGE, 30.0);
        System.out.println("Hourly rates configured.");

        // 2. Create Parking Spots
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        
        ParkingSpot electricSpot = new ParkingSpot(1, 1, Type.SMALL);
        // Use the shared service instance
        initializeAndAddService(electricSpot, sharedElectricService);
        
        ParkingSpot mediumSpot1 = new ParkingSpot(1, 2, Type.MEDIUM);
        ParkingSpot mediumSpot2 = new ParkingSpot(2, 1, Type.MEDIUM);
        ParkingSpot largeSpot = new ParkingSpot(2, 2, Type.LARGE);

        initializeSpotServices(mediumSpot1);
        initializeSpotServices(mediumSpot2);
        initializeSpotServices(largeSpot);
        
        parkingSpots.add(electricSpot);
        parkingSpots.add(mediumSpot1);
        parkingSpots.add(mediumSpot2);
        parkingSpots.add(largeSpot);

        System.out.println("Created " + parkingSpots.size() + " parking spots (all initialized).");

        // 3. Create Entry Gates & Distance Repository
        List<EntryGate> entryGates = new ArrayList<>();
        Constructor<EntryGate> entryConstructor = EntryGate.class.getConstructor(int.class, ISpotAllocationStrategy.class);
        entryConstructor.setAccessible(true);
        EntryGate entryGate1 = entryConstructor.newInstance(1, null);
        EntryGate entryGate2 = entryConstructor.newInstance(2, null);
        entryGates.add(entryGate1);
        entryGates.add(entryGate2);

        DistanceRepository distanceRepository = new DistanceRepository(entryGates, parkingSpots);
        System.out.println("Distance Repository initialized.");
        
        ISpotAllocationStrategy strategy1 = new NearestSpotAllocationStrategy(entryGate1, distanceRepository);
        ISpotAllocationStrategy strategy2 = new NearestSpotAllocationStrategy(entryGate2, distanceRepository);
        setFinalField(entryGate1, "spotAllocationStrategy", strategy1);
        setFinalField(entryGate2, "spotAllocationStrategy", strategy2);
        System.out.println("Entry gates 1 and 2 configured with NearestSpotAllocationStrategy.");

        System.out.println("============== Parking Lot Setup Complete ==============\n");

        // SCENARIO 1: A standard medium car parks.
        System.out.println("---------- SCENARIO 1: Standard Vehicle Parking ----------");
        Vehicle mediumCar = new Vehicle(Type.MEDIUM);
        initializeVehicleServices(mediumCar);
        System.out.println("A MEDIUM vehicle approaches Entry Gate 1.");
        setField(entryGate1, "vehicle", mediumCar);
        Ticket mediumCarTicket = entryGate1.processEntry();
        System.out.println("----------------------------------------------------------\n");


        // SCENARIO 2: An electric small car parks and requires charging.
        System.out.println("---------- SCENARIO 2: Electric Vehicle Parking ----------");
        Vehicle electricCar = new Vehicle(Type.SMALL);
        initializeVehicleServices(electricCar);
        Map<String, Object> evConfig = new HashMap<>();
        evConfig.put("spotId", "F1-S1");
        evConfig.put("duration", 2);
        evConfig.put("fastCharging", true);
        // Use the shared service instance
        electricCar.addService(sharedElectricService, evConfig);
        
        System.out.println("A SMALL electric vehicle (requiring charging) approaches Entry Gate 2.");
        setField(entryGate2, "vehicle", electricCar);
        Ticket electricCarTicket = entryGate2.processEntry();
        System.out.println("----------------------------------------------------------\n");

        // SCENARIO 3: A large car tries to park, but the only large spot is taken.
        System.out.println("---------- SCENARIO 3: No Spot Available ----------");
        Vehicle largeCar1 = new Vehicle(Type.LARGE);
        initializeVehicleServices(largeCar1);
        System.out.println("A LARGE vehicle approaches Entry Gate 1 to take the only large spot.");
        setField(entryGate1, "vehicle", largeCar1);
        Ticket largeCarTicket1 = entryGate1.processEntry();

        Vehicle largeCar2 = new Vehicle(Type.LARGE);
        initializeVehicleServices(largeCar2);
        System.out.println("\nA second LARGE vehicle approaches Entry Gate 2.");
        setField(entryGate2, "vehicle", largeCar2);
        entryGate2.processEntry();
        System.out.println("---------------------------------------------------\n");


        // SCENARIO 4: The medium car exits after 3 hours.
        System.out.println("---------- SCENARIO 4: Standard Vehicle Exit ----------");
        System.out.println("The MEDIUM vehicle is now exiting.");
        setFinalField(mediumCarTicket, "entryTime", LocalDateTime.now().minusHours(3));
        ExitGate exitGate1 = new ExitGate(1, mediumCarTicket, hourlyRates);
        exitGate1.processExit();
        System.out.println("-------------------------------------------------------\n");


        // SCENARIO 5: The electric car exits after 2 hours.
        System.out.println("---------- SCENARIO 5: Electric Vehicle Exit ----------");
        System.out.println("The SMALL electric vehicle is now exiting.");
        // This will now work because electricCarTicket is not null
        setFinalField(electricCarTicket, "entryTime", LocalDateTime.now().minusHours(2));
        ExitGate exitGate2 = new ExitGate(2, electricCarTicket, hourlyRates);
        exitGate2.processExit();
        System.out.println("-------------------------------------------------------\n");

    }

    // --- Helper methods ---

    private static void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
    
    private static void setFinalField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    private static void initializeAndAddService(ParkingSpot spot, IParkingService service) throws Exception {
        Field supportedServicesField = ParkingSpot.class.getDeclaredField("supportedServices");
        supportedServicesField.setAccessible(true);
        List<IParkingService> services = new ArrayList<>();
        services.add(service);
        supportedServicesField.set(spot, services);
    }

    private static void initializeSpotServices(ParkingSpot spot) throws Exception {
        Field supportedServicesField = ParkingSpot.class.getDeclaredField("supportedServices");
        supportedServicesField.setAccessible(true);
        supportedServicesField.set(spot, new ArrayList<>());
    }

    private static void initializeVehicleServices(Vehicle vehicle) throws Exception {
        Field requiredServicesField = Vehicle.class.getDeclaredField("requiredServices");
        requiredServicesField.setAccessible(true);
        requiredServicesField.set(vehicle, new ArrayList<>());

        Field servicesConfigField = Vehicle.class.getDeclaredField("servicesConfig");
        servicesConfigField.setAccessible(true);
        servicesConfigField.set(vehicle, new ArrayList<>());
    }
}