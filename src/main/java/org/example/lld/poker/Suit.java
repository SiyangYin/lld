package org.example.lld.poker;

public enum Suit {
    SPADE(0),
    HEART(1),
    CLUB(2),
    DIAMOND(3);

    final int number;
    Suit(int number) {
        this.number = number;
    }
}
