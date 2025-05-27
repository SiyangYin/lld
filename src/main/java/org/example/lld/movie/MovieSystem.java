package org.example.lld.movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSystem {
    List<Movie> movies;

    public static void main(String[] args) {
        MovieSystem movieSystem = new MovieSystem();
        List<Filter> filters = List.of(new NameFilter(""), new TypeFilter(""));
        List<Movie> movies = movieSystem.search(movieSystem.movies, filters, new And());
        movies = movieSystem.search(movieSystem.movies, filters, new Or());
        Customer customer = new Customer();
        for (Movie movie : movies) {
            if (movieSystem.checkAvailable(movie, 1, 1)) {
                movieSystem.book(customer, movie, 1, 1);
                movieSystem.cancel(customer);
            }
        }
    }

    List<Movie> search(List<Movie> movies, List<Filter> filters, Operator op) {
        List<Movie> res = new ArrayList<>();
        for (Movie movie : movies) {
            op.apply(movie, res, filters);
        }
        return res;
    }

    boolean checkAvailable(Movie movie, int row, int col) {
        return movie.seatMap.seats[row][col].available;
    }

    void book(Customer customer, Movie movie, int row, int col) {
        Seat seat = movie.seatMap.seats[row][col];
        customer.ticket = new Ticket(movie, seat);
        customer.payTicket(customer.ticket, new Card("", ""));
        seat.customer = customer;
        seat.available = false;
    }

    void cancel(Customer customer) {
        Seat seat = customer.ticket.seat;
        customer.ticket = null;
        seat.customer = null;
        seat.available = true;
    }
}

interface Filter {
    boolean apply(Movie movie);
}

class NameFilter implements Filter {
    String name;
    public NameFilter(String name) {
        this.name = name;
    }
    public boolean apply(Movie movie) {
        return name.equals(movie.name);
    }
}

class TypeFilter implements Filter {
    String type;
    public TypeFilter(String type) {
        this.type = type;
    }
    public boolean apply(Movie movie) {
        return type.equals(movie.type);
    }
}

interface Operator {
    void apply(Movie movie, List<Movie> res, List<Filter> filters);
}

class And implements Operator {
    public void apply(Movie movie, List<Movie> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (!filter.apply(movie)) return;
        }
        res.add(movie);
    }
}

class Or implements Operator {
    public void apply(Movie movie, List<Movie> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.apply(movie)) {
                res.add(movie);
                return;
            }
        }
    }
}

class Customer {
    String name;
    Ticket ticket;
    Payment payment;
    void payTicket(Ticket ticket, Payment payment) {
        payment.pay(ticket.price);
    }
}

class Movie {
    int id;
    String name;
    String type;
    SeatMap seatMap;
}

class SeatMap {
    Seat[][] seats;
}

class Seat {
    int row;
    int col;
    boolean available;
    Customer customer;
}

class Ticket {
    int id;
    int price;
    Movie movie;
    Seat seat;
    public Ticket(Movie movie, Seat seat) {
        this.movie = movie;
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