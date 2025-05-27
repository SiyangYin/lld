package org.example.lld.elevator;

import java.util.*;

public class ElevatorSystem {
    private static ElevatorSystem instance;
    List<Elevator> elevators;
    Deque<Request> q;
    int n;
    private ElevatorSystem (int n) {
        this.n = n;
        elevators = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            elevators.add(new Elevator());
        }
        q = new ArrayDeque<>();
    };
    public static ElevatorSystem getInstance() {
        if (instance == null) {
            instance = new ElevatorSystem(10);
        }
        return instance;
    }

    void run() {
        while (!q.isEmpty()) processRequest();
    }

    void createRequest(int from, int to) {
        Request request = new Request(from, to);
        q.offer(request);
    }

    void processRequest() {
        Request request = q.peek();
        Elevator elevator = findElevator(request);
        if (elevator != null) {
            new Thread(() -> elevator.takeRequest(request)).start();
            q.poll();
        }
    }

    Elevator findElevator(Request request) {
        elevators.sort(Comparator.comparingInt(i -> Math.abs(i.cur - request.from)));
        for (Elevator elevator : elevators) {
            if (elevator.status == Status.AVAILABLE ||
                    elevator.status == Status.PICK && elevator.cur <= elevator.from && elevator.cur <= request.from && (request.from <= request.to && request.to <= elevator.from || elevator.from <= elevator.to && request.from <= request.to) ||
                    elevator.status == Status.PICK && elevator.cur >= elevator.from && elevator.cur >= request.from && (request.from >= request.to && request.to >= elevator.from || elevator.from >= elevator.to && request.from >= request.to) ||
                    elevator.status == Status.SEND && elevator.cur <= elevator.to && elevator.cur <= request.from && (elevator.to <= request.from || elevator.from <= elevator.to && request.from <= request.to) ||
                    elevator.status == Status.SEND && elevator.cur >= elevator.to && elevator.cur >= request.from && (elevator.to >= request.from || elevator.from >= elevator.to && request.from >= request.to)) return elevator;
        }
        return null;
    }
}

class User {
    public static void main(String[] args) {
        ElevatorSystem system = ElevatorSystem.getInstance();
        new Thread(() -> system.run()).start();
        system.createRequest(1, 1);
    }
}


enum Status {
    AVAILABLE,
    PICK,
    SEND;
}

class Request {
    int id;
    int from;
    int to;
    Request(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Elevator {
    Status status;
    int cur;
    int from;
    int to;

    void takeRequest(Request request) {
        this.from= request.from;
        this.to= request.to;
        pick(from);
        send(to);
    }

    void pick(int from) {
        while (cur < from) cur++;
        while (cur > from) cur--;
    }

    void send(int to) {
        while (cur < to) cur++;
        while (cur > to) cur--;
    }
}

