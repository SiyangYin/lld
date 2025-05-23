package org.example.lld.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.example.lld.game.PlayerState.FOLD;

public class Dealer {
    Deck deck;

    Dealer() {
        deck = new Deck();
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck.cards);
    }

    public void burn() {
        deck.cards.removeFirst();
    }

    public Card deal() {
        return deck.cards.removeFirst();
    }

    public void dealToPlayers(List<Player> players) {
        burn();
        players.forEach(player -> player.hand.add(deal()));
    }

    public void dealToTable(List<Player> players) {
        burn();
        Card card = deal();
        players.stream().filter(player -> player.state != FOLD).forEach(player -> player.hand.add(card));
    }

    public void dealHole(List<Player> players) {
        for (int i = 0; i < 2; i++) {
            dealToPlayers(players);
        }
    }

    public void dealFlop(List<Player> players) {
        for (int i = 0; i < 3; i++) {
            dealToTable(players);
        }
    }

    public void dealTurn(List<Player> players) {
        dealToTable(players);
    }

    public void dealRiver(List<Player> players) {
        dealToTable(players);
    }
}
