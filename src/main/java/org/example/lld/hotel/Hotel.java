package org.example.lld.hotel;

import java.util.LinkedList;
import java.util.Map;

public class Hotel {
    Map<RoomType, LinkedList<Room>> available;
    Map<RoomType, Map<Integer, Room>> occupied;

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Guest guest = new Guest();
        if (hotel.checkAvailable(RoomType.MEDIUM)) {
            hotel.book(guest, RoomType.MEDIUM);
            hotel.checkin(guest);
            hotel.checkout(guest);
            hotel.cancel(guest);
        };
    }

    boolean checkAvailable(RoomType roomType) {
        return !available.get(roomType).isEmpty();
    }

    void book(Guest guest, RoomType roomType) {
        if (checkAvailable(roomType)) {
            Room room = available.get(roomType).removeFirst();
            guest.booking = new Booking(room);
            guest.pay(guest.booking, new Card("", ""));
            room.status = RoomStatus.BOOKED;
            occupied.get(roomType).put(room.id, room);
        }
    }

    void cancel(Guest guest) {
        Room room = guest.booking.room;
        room.status = RoomStatus.AVAILABLE;
        room.guest = null;
        guest.booking = null;
        occupied.get(room.roomType).remove(room.id);
        available.get(room.roomType).add(room);
    }

    void checkin(Guest guest) {
        Room room = guest.booking.room;
        room.guest = guest;
        room.status = RoomStatus.CHECKIN;
    }

    void checkout(Guest guest) {
        Room room = guest.booking.room;
        room.status = RoomStatus.AVAILABLE;
        room.guest = null;
        guest.booking = null;
        occupied.get(room.roomType).remove(room.id);
        available.get(room.roomType).add(room);
    }
}

enum RoomType {
    SMALL, MEDIUM, LARGE
}

enum RoomStatus {
    AVAILABLE, BOOKED, CHECKIN
}

class Room {
    int id;
    RoomType roomType;
    RoomStatus status;
    Guest guest;
}

class Guest {
    int id;
    Booking booking;
    Payment payment;
    void pay(Booking booking, Payment payment) {
        payment.pay(booking.price);
    }
}

class Booking {
    int id;
    Room room;
    int price;
    Booking(Room room) {
        this.room = room;
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