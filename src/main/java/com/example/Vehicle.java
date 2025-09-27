package com.example;

import java.util.List;
import java.util.Map;

public class Vehicle {
    private final Type type;
    private List<IParkingService> requiredServices;
    private List<Map<String, Object>> servicesConfig;

    public Vehicle(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void addService(IParkingService service, Map<String, Object> config) {
        this.requiredServices.add(service);
        this.servicesConfig.add(config);
    }

    public List<IParkingService> getRequiredServices() {
        return requiredServices;
    }

    public List<Map<String, Object>> getServicesConfig() {
        return servicesConfig;
    }
}
