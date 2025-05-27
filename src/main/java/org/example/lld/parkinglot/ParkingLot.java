package org.example.lld.parkinglot;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.example.lld.parkinglot.SpotType.*;
import static org.example.lld.parkinglot.VehicleType.*;

public class ParkingLot {
    int id;
    List<Floor> floors;

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        Vehicle car = new Car();
        for (Floor floor : parkingLot.floors) {
            if (floor.checkAvailable(car)) {
                floor.checkin(car);
                floor.checkout(car);
                break;
            }
        }
    }
}

class Floor {
    int number;
    Map<SpotType, LinkedList<ParkingSpot>> available;
    Map<SpotType, Map<Integer, ParkingSpot>> occupied;
    Map<VehicleType, SpotType> map = Map.of(BUS, LARGE, CAR, MEDIUM, MOTORCYCLE, SMALL);

    boolean checkAvailable(Vehicle vehicle) {
        VehicleType vehicleType = vehicle.vehicleType;
        SpotType spotType = map.get(vehicleType);
        return !available.get(spotType).isEmpty();
    }

    void checkin(Vehicle vehicle) {
        if (checkAvailable(vehicle)) {
            VehicleType vehicleType = vehicle.vehicleType;
            SpotType spotType = map.get(vehicleType);
            ParkingSpot spot = available.get(spotType).removeFirst();
            spot.take(vehicle);
            vehicle.ticket = new Ticket(spot, Timestamp.from(Instant.now()));
            occupied.get(spotType).put(spot.id, spot);
        }
    }

    void checkout(Vehicle vehicle) {
        ParkingSpot spot = vehicle.ticket.spot;
        vehicle.ticket.endTime = Timestamp.from(Instant.now());
        vehicle.payTicket(vehicle.ticket, new Cash(100));
        spot.free();
        SpotType spotType = spot.spotType;
        occupied.get(spotType).remove(spot.id);
        available.get(spotType).add(spot);
        vehicle.ticket = null;
    }

}

enum SpotType {
    LARGE, MEDIUM, SMALL
}

abstract class ParkingSpot {
    int id;
    SpotType spotType;
    boolean available;
    Vehicle vehicle;
    void take(Vehicle vehicle) {
        this.vehicle = vehicle;
        available = false;
    }

    void free() {
        this.vehicle = null;
        available = true;
    }
}

class LargeSpot extends ParkingSpot {

}

class MediumSpot extends ParkingSpot {

}

class SmallSpot extends ParkingSpot {

}

enum VehicleType {
    BUS, CAR, MOTORCYCLE
}

abstract class Vehicle {
    int id;
    VehicleType vehicleType;
    Ticket ticket;
    Payment payment;
    void payTicket(Ticket ticket, Payment payment) {
        payment.pay(ticket.price);
    }
}

class Bus extends Vehicle {

}

class Car extends Vehicle {

}

class Motorcycle extends Vehicle {

}

class Ticket {
    int id;
    int price;
    ParkingSpot spot;
    Timestamp startTime;
    Timestamp endTime;
    Ticket(ParkingSpot spot, Timestamp startTime) {
        this.spot = spot;
        this.startTime = startTime;
    }
}

abstract class Payment {
    abstract void pay(int amount);
}

class Card extends Payment {
    String cardNumber;
    String code;
    public Card(String cardNumber, String code) {
        this.cardNumber = cardNumber;
        this.code = code;
    }
    public void pay(int amount) {};
}

class Cash extends Payment {
    int value;
    public Cash(int value) {
        this.value = value;
    }
    public void pay(int amount) {};
}

class Transfer extends Payment {
    String bank;
    String accountNumber;
    String routingNumber;
    public Transfer(String bank, String accountNumber, String routingNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    public void pay(int amount) {};
}