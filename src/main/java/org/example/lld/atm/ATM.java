package org.example.lld.atm;

public class ATM {
    State state;

    ATM() {
        state = State.IDLE;
    }

    public void startWorking() {
        while (true) {
            switch (state) {
                case IDLE:
                    break;
                case INSERTING_CARD:

            }
        }
    }

    public void insertCard() {

    }


}
