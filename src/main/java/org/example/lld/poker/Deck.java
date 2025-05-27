package org.example.lld.poker;

import java.util.LinkedList;

public class Deck {
    LinkedList<Card> cards;
    Deck(LinkedList<Card> cards) {
        this.cards = cards;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
}
