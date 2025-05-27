package org.example.lld.poker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.example.lld.poker.PlayerState.FOLD;

abstract class Round {
    Deal deal;
    public Round() {}
    public Round(Deal deal) {
        this.deal = deal;
    }
    void playRound(LinkedList<Player> players, int maxBet) {
        for (Player player : players) {
            if (player.state != FOLD) {
                maxBet = Math.max(maxBet, player.action.apply(player, player.bet, maxBet));
            }
        }
    }
    void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
        deal.apply(dealer, players);
        playRound(players, maxBet);
    }
}

class Hole extends Round {
    public Hole(Deal deal) {
        super(deal);
    }
//    public void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
//        deal.apply(dealer, players);
//        playRound(players, maxBet);
//    }
}

class Flop extends Round {
    public Flop(Deal deal) {
        super(deal);
    }
//    public void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
//        deal.apply(dealer, players);
//        playRound(players, maxBet);
//    }
}

class Turn extends Round {
    public Turn(Deal deal) {
        super(deal);
    }
//    public void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
//        deal.apply(dealer, players);
//        playRound(players, maxBet);
//    }
}

class River extends Round {
    public River(Deal deal) {
        super(deal);
    }
//    public void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
//        deal.apply(dealer, players);
//        playRound(players, maxBet);
//    }
}

class Showdown extends Round {
    public void apply(Dealer dealer, LinkedList<Player> players, int maxBet) {
        players.forEach(Player::evaluate);
        players.sort(Comparator.comparing(p -> p.value.number));
        Player winner = players.removeLast();
        winner.win(players);
    }
}

interface Action {
    int apply(Player player, int bet, int maxBet);
}

class Fold implements Action {
    public int apply(Player player, int bet, int maxBet) {
        return maxBet;
    }
}

class Check implements Action {
    public int apply(Player player, int bet, int maxBet) {
        return maxBet;
    }
}

class Call implements Action {
    public int apply(Player player, int bet, int maxBet) {
        player.setBet(maxBet);
        return maxBet;
    }
}

class Raise implements Action {
    public int apply(Player player, int bet, int maxBet) {
        player.setBet(bet);
        return bet;
    }
}

class Allin implements Action {
    public int apply(Player player, int bet, int maxBet) {
        player.setBet(player.money);
        return player.money;
    }
}

interface Deal {
    void apply(Dealer dealer, LinkedList<Player> players);
}

class DealHole implements Deal {
    public void apply(Dealer dealer, LinkedList<Player> players) {
        for (int i = 0; i < 2; i++) {
            dealer.dealToPlayers(players);
        }
    }
}

class DealFlop implements Deal {
    public void apply(Dealer dealer, LinkedList<Player> players) {
        for (int i = 0; i < 3; i++) {
            dealer.dealToTable(players);
        }
    }
}

class DealTurn implements Deal {
    public void apply(Dealer dealer, LinkedList<Player> players) {
        dealer.dealToTable(players);
    }
}

class DealRiver implements Deal {
    public void apply(Dealer dealer, LinkedList<Player> players) {
        dealer.dealToTable(players);
    }
}


public class Game {
    GameState state;
    Dealer dealer;
    LinkedList<Player> players;
    int maxBet;

    Game(Dealer dealer, LinkedList<Player> players) {
        this.dealer = dealer;
        this.players = players;
    }

    public static void main(String[] args) {
        Game game = new Game(new Dealer(new Deck(new LinkedList<>())), new LinkedList<>());
        game.start();
    }

    public void start() {
        List<Round> rounds = List.of(new Hole(new DealHole()), new Flop(new DealFlop()), new Turn(new DealTurn()), new River(new DealRiver()), new Showdown());
        while (true) {
            for (Round round : rounds) {
                round.apply(dealer, players, maxBet);
            }
//            switch (state) {
//                case HOLE:
//                    hole();
//                    break;
//                case FLOP:
//                    flop();
//                    break;
//                case TURN:
//                    turn();
//                    break;
//                case RIVER:
//                    river();
//                    break;
//                case SHOWDOWN:
//                    showdown();
//                    break;
//            }
        }
    }

//    public void hole() {
//        dealer.dealHole(players);
//        round();
//        state = GameState.FLOP;
//    }
//
//    public void flop() {
//        dealer.dealFlop(players);
//        round();
//        state = GameState.TURN;
//    }
//
//    public void turn() {
//        dealer.dealTurn(players);
//        round();
//        state = GameState.RIVER;
//    }
//
//    public void river() {
//        dealer.dealRiver(players);
//        round();
//        state = GameState.SHOWDOWN;
//    }
//
//    public void showdown() {
//        players.forEach(Player::evaluate);
//        players.sort(Comparator.comparing(p -> p.value.number));
//        Player winner = players.removeLast();
//        winner.win(players);
//        state = GameState.HOLE;
//    }
//
//    public void round() {
//        players.stream().filter(player -> player.state != FOLD).forEach(player -> maxBet = player.act(maxBet));
//    }

}
