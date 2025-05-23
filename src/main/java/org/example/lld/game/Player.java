package org.example.lld.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.lld.game.PlayerState.FOLD;
import static org.example.lld.game.Value.*;

public class Player {
    PlayerState state;
    List<Card> hand;
    Value value;
    int money;
    int bet;

    Player() {
        hand = new ArrayList<>();
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void bet(int bet) {
        setBet(bet);
    }

    public void fold() {}

    public void check() {}

    public void call(int bet) {
        setBet(bet);
    }

    public void raise(int bet) {
        setBet(bet);
    }

    public void allin() {
        setBet(money);
    }

    public int act(int maxBet) {
        System.out.println("The current max bet is " + maxBet + ", PLease give your option followed by amount: BET, FOLD, CHECK, CALL, RAISE: ");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        state = PlayerState.valueOf(option);
        int bet = scanner.nextInt();
        switch (state) {
            case BET:
                bet(bet);
                break;
            case FOLD:
                fold();
                break;
            case CHECK:
                check();
                break;
            case CALL:
                call(maxBet);
                break;
            case RAISE:
                raise(bet);
                break;
            case ALLIN:
                allin();
                break;
        }
        return Math.max(maxBet, bet);
    }

    public void evaluate() {
        if (state == FOLD) {
            value = NONE;
            return;
        }
        int pairs = 0;
        int consec = 0;
        int consecMax = 0;
        boolean straight = false;
        boolean flush = false;
        boolean three = false;
        boolean four = false;
        boolean royal = false;
        int[] suitCnt = new int[4];
        int[] rankCnt = new int[14];
        hand.forEach(card -> {
            suitCnt[card.suit.number]++;
            rankCnt[card.rank.number]++;
        });
        for (int cnt : suitCnt) {
            if (cnt >= 5) {
                flush = true;
                break;
            }
        }
        for (int cnt : rankCnt) {
            if (cnt == 4) four = true;
            else if (cnt == 3) three = true;
            else if (cnt == 2) pairs++;
            if (cnt > 0) {
                consec++;
                consecMax = Math.max(consecMax, consec);
            }
            else consec = 0;
        }
        if (rankCnt[1] > 0) {
            consec++;
            if (consec >= 5) royal = true;
            consecMax = Math.max(consecMax, consec);
        }
        if (consecMax == 5) straight = true;

        if (royal && flush) value = Value.ROYAL_FLUSH;
        else if (straight && flush) value = Value.STRAIGHT_FLUSH;
        else if (four) value = Value.FOUR_OF_A_KIND;
        else if (three && pairs > 0) value = FULL_HOUSE;
        else if (flush) value = FLUSH;
        else if (straight) value = STRAIGHT;
        else if (three) value = THREE_OF_A_KIND;
        else if (pairs == 2) value = TWO_PAIRS;
        else if (pairs == 1) value = PAIR;
        else value = HIGH_CARD;
    }

    public void lose() {
        money -= bet;
        bet = 0;
    }

    public void win(List<Player> players) {
        players.forEach(player -> {
            money += player.bet;
            player.lose();
        });
        bet = 0;
    }
}
