package org.example.lld.poker;

import java.util.Comparator;
import java.util.LinkedList;

import static org.example.lld.poker.PlayerState.FOLD;

public class Game {
    GameState state;
    Dealer dealer;
    LinkedList<Player> players;
    int maxBet;

    Game() {
        dealer = new Dealer();
        players = new LinkedList<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void start() {
        while (true) {
            switch (state) {
                case HOLE:
                    hole();
                    break;
                case FLOP:
                    flop();
                    break;
                case TURN:
                    turn();
                    break;
                case RIVER:
                    river();
                    break;
                case SHOWDOWN:
                    showdown();
                    break;
            }
        }
    }

    public void hole() {
        dealer.dealHole(players);
        round();
        state = GameState.FLOP;
    }

    public void flop() {
        dealer.dealFlop(players);
        round();
        state = GameState.TURN;
    }

    public void turn() {
        dealer.dealTurn(players);
        round();
        state = GameState.RIVER;
    }

    public void river() {
        dealer.dealRiver(players);
        round();
        state = GameState.SHOWDOWN;
    }

    public void showdown() {
        players.forEach(Player::evaluate);
        players.sort(Comparator.comparing(p -> p.value.number));
        Player winner = players.removeLast();
        winner.win(players);
        state = GameState.HOLE;
    }

    public void round() {
        players.stream().filter(player -> player.state != FOLD).forEach(player -> maxBet = player.act(maxBet));
    }

}
