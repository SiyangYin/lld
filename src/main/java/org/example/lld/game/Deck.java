package org.example.lld.game;

import java.util.LinkedList;

public class Deck {
    LinkedList<Card> cards;
    Deck() {
        cards = new LinkedList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
}
