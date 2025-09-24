package com.example;

import java.util.List;

public class Vehicle {
    private final Type type;
    private List<IParkingService> requiredServices;

    public Vehicle(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void addService(IParkingService service) {
        this.requiredServices.add(service);
    }

    public List<IParkingService> getRequiredServices() {
        return requiredServices;
    }
}
