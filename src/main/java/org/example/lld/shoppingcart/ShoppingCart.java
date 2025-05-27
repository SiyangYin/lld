package org.example.lld.shoppingcart;

import java.util.List;
import java.util.Map;

class Account {
    int id;
    String username;
    String password;
    ShoppingCart shoppingCart;

    public static void main(String[] args) {
        Account account = new Account();
        Item item1 = new Item("", 1);
        Item item2 = new Item("", 2);
        account.shoppingCart.add(item1, 1);
        account.shoppingCart.add(item2, 2);
        account.shoppingCart.remove(item1);
        account.shoppingCart.checkoutAll(new Card("", ""));
    }
}

public class ShoppingCart {

    Map<Item, Integer> cart;

    void add(Item item, int quantity) {
        cart.merge(item, quantity, Integer::sum);
    }

    void remove(Item item) {
        cart.remove(item);
    }

    void decrease(Item item, int quantity) {
        cart.merge(item, -quantity, Integer::sum);
        if (cart.get(item) <= 0) {
            cart.remove(item);
        }
    }

    void checkout(List<Item> items, Payment payment) {
        int total = 0;
        for (Item item : items) {
            if (cart.containsKey(item)) {
                total += cart.get(item) * item.price;
                cart.remove(item);
            }
        }
        payment.pay(total);
    }

    void checkoutAll(Payment payment) {
        int total = 0;
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            total += entry.getKey().price * entry.getValue();
//            cart.remove(entry.getKey());
        }
        cart.clear();
        payment.pay(total);
    }
}

class Item {
    int id;
    String name;
    int price;
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

abstract class Payment {
    abstract void pay(int amount);
}

class Card extends Payment {
    String cardNumber;
    String code;
    public Card(String cardNumber, String code) {
        this.cardNumber = cardNumber;
        this.code = code;
    }
    public void pay(int amount) {};
}

class Cash extends Payment {
    int value;
    public Cash(int value) {
        this.value = value;
    }
    public void pay(int amount) {};
}

class Transfer extends Payment {
    String bank;
    String accountNumber;
    String routingNumber;
    public Transfer(String bank, String accountNumber, String routingNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    public void pay(int amount) {};
}