package org.example.lld.elevator;

import java.util.Scanner;

public class Elevator {
    State state;
    int currentFloor;
    int fromFloor;
    int toFloor;

    public void startWorking() {
        while (true) {
            switch (state) {
                case IDLE:
                    break;
                case AVAILABLE:
                    call();
                case MOVING_UP:
                    moveUp();
                case MOVING_DOWN:
                    moveDown();
            }
        }
    }

    public void call() {
        Scanner scanner = new Scanner(System.in);
        int fromFloor = scanner.nextInt();
        int toFloor = scanner.nextInt();

    }

    public void moveUp() {
        while (currentFloor < toFloor) {
            currentFloor++;
        }
    }

    public void moveDown() {
        while (currentFloor > toFloor) {
            currentFloor--;
        }
    }
}
