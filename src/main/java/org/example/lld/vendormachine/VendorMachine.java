package org.example.lld.vendormachine;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class VendorMachine {
    State state;
    Item selectedItem;
    int acceptedMoney;
    Map<ItemType, Integer> inventory;
    Map<ItemType, Integer> item2price;

    VendorMachine() {
        state = State.SELECTING_ITEM;
        inventory = new HashMap<>();
        item2price = new HashMap<>();
    }

    public void startWorking() throws InterruptedException {
        while (true) {
            switch (state) {
//                case IDLE:
//                    Thread.sleep(10);
//                    break;
                case SELECTING_ITEM:
                    selectingItem();
                    break;
                case ACCEPTING_MONEY:
                    acceptingMoney();
                    break;
            }
        }
    }

//    public void startSelecting() {
//        state = State.SELECTING_ITEM;
//    }

    public void selectingItem() {
        Item item = getSelectedItemFromUser();
        boolean available = checkAvailability(item);
        System.out.println("THe item you have selected is: " + (available ? "Available" : "Not Available"));
        if (available) {
            selectedItem = item;
            state = State.ACCEPTING_MONEY;
        }
//        else {
//            state = State.IDLE;
//        }
    }

    public void acceptingMoney() {
        while (acceptedMoney < selectedItem.price) {
            acceptedMoney += acceptMoneyFromUser();
            System.out.println("You have inserted " + acceptedMoney + " dollars");
        }
        giveItem();
        returnChange();
        state = State.SELECTING_ITEM;
    }

    public Item getSelectedItemFromUser() {
        System.out.println("Please select the item you would like to buy: COKE, PEPSI, JUICE, SODA");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        ItemType itemType = ItemType.valueOf(input);
        Item item = new Item(itemType, item2price.getOrDefault(itemType, 10));
        System.out.println("The price for " + itemType + " is " + item.price);
        return item;
    }

    public boolean checkAvailability(Item item) {
        return inventory.getOrDefault(item.type, 1) > 0;
    }

    public int acceptMoneyFromUser() {
        System.out.println("Please insert money: ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        return input;
    }

    public Item giveItem() {
        System.out.println("Here is your item: " + selectedItem.type);
//        inventory.merge(selectedItem.type, -1, Integer::sum);
        return selectedItem;
    }

    public int returnChange() {
        int change = acceptedMoney - selectedItem.price;
        System.out.println("Here is your change: " + change);
        acceptedMoney = 0;
        return change;
    }

}
