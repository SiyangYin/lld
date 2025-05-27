package org.example.lld.airline;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Airline {
    List<Flight> flights;

    public static void main(String[] args) {
        Airline airline = new Airline();
        List<Filter> filters = List.of(new DepartureFilter(new Airport()), new ArrivalFilter(new Airport()), new TimeFilter(Timestamp.from(Instant.now()), Timestamp.from(Instant.now())));
        List<Flight> flights = airline.search(airline.flights, filters, new And());
        flights = airline.search(airline.flights, filters, new Or());
        Passenger passenger = new Passenger();
        for (Flight flight : flights) {
            if (airline.checkAvailable(flight, SeatType.ECONOMY, 1, 1)) {
                airline.book(passenger, flight, SeatType.ECONOMY, 1, 1);
                airline.cancel(passenger);
            }
        }
    }

    List<Flight> search(List<Flight> flights, List<Filter> filters, Operator op) {
        List<Flight> res = new ArrayList<>();
        for (Flight flight : flights) {
            op.apply(flight, res, filters);
        }
        return res;
    }

    boolean checkAvailable(Flight flight, SeatType seatType, int row, int col) {
        return flight.seatMaps.get(seatType).seats[row][col].available;
    }

    void book(Passenger passenger, Flight flight, SeatType seatType, int row, int col) {
        Seat seat = flight.seatMaps.get(seatType).seats[row][col];
        passenger.ticket = new Ticket(flight, seat);
        passenger.payTicket(passenger.ticket, new Card("", ""));
        seat.passenger = passenger;
        seat.available = false;
    }

    void cancel(Passenger passenger) {
        Seat seat = passenger.ticket.seat;
        passenger.ticket = null;
        seat.passenger = null;
        seat.available = true;
    }
}

interface Operator {
    void apply(Flight flight, List<Flight> flights, List<Filter> filters);
}

class And implements Operator {
    public void apply(Flight flight, List<Flight> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (!filter.apply(flight)) return;
        }
        res.add(flight);
    }
}

class Or implements Operator {
    public void apply(Flight flight, List<Flight> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.apply(flight)) {
                res.add(flight);
                return;
            }
        }
    }
}

interface Filter {
    boolean apply(Flight flight);
}

class DepartureFilter implements Filter {
    Airport departure;
    public DepartureFilter(Airport departure) {
        this.departure = departure;
    }
    public boolean apply(Flight flight) {
        return departure.equals(flight.departure);
    }
}

class ArrivalFilter implements Filter {
    Airport arrival;
    public ArrivalFilter(Airport arrival) {
        this.arrival = arrival;
    }
    public boolean apply(Flight flight) {
        return arrival.equals(flight.arrival);
    }
}

class TimeFilter implements Filter {
    Timestamp start;
    Timestamp end;
    public TimeFilter(Timestamp start, Timestamp end) {
        this.start = start;
        this.end = end;
    }
    public boolean apply(Flight flight) {
        return start.before(flight.departureTime) && end.after(flight.departureTime);
    }
}

class Airport {
    String name;
}

class Flight {
    int id;
    Airport departure;
    Airport arrival;
    Timestamp departureTime;
    Map<SeatType, SeatMap> seatMaps;
}

enum SeatType {
    ECONOMY, BUSINESS, FIRST_CLASS;
}

class Seat {
    int id;
    int row;
    int col;
    SeatType seatType;
    boolean available;
    Passenger passenger;
}

class SeatMap {
    SeatType seatType;
    Seat[][] seats;
}

class Passenger {
    int id;
    String name;
    Ticket ticket;
    Payment payment;
    void payTicket(Ticket ticket, Payment payment) {
        payment.pay(ticket.price);
    }
}

class Ticket {
    int id;
    int price;
    Flight flight;
    Seat seat;
    public Ticket(Flight flight, Seat seat) {
        this.flight = flight;
        this.seat = seat;
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