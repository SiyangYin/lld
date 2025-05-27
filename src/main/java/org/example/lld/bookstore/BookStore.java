package org.example.lld.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookStore {
    List<Book> books;
    Map<String, List<Book>> bookByTitle;
    Map<String, List<Book>> bookBySubject;
    Map<String, List<Book>> bookByAuthor;
    Map<String, List<Book>> bookByPublisher;
    Map<String, List<Book>> bookByLanguage;
    Map<String, List<Book>> bookByGenre;

    public static void main(String[] args) {
        BookStore bookStore = new BookStore();
        List<Filter> filters = List.of(new TitleFilter(""), new SubjectFilter(""), new AuthorFilter(""), new PublisherFilter(""), new LanguageFilter(""));
        List<Book> books = bookStore.search(bookStore.books, filters, new And());
        books = bookStore.search(bookStore.books, filters, new Or());
        User user = new User();
        for (Book book : books) {
            if (bookStore.checkAvailable(book)) {
                bookStore.lend(user, book);
                user.returnBook(book);
                user.buy(book, new Transfer("", "", ""));
            }
        }
    }

    public List<Book> search(List<Book> books, List<Filter> filters, Operator op) {
        List<Book> res = new ArrayList<>();
        for (Book book : books) {
            op.apply(book, res, filters);
        }
        return res;
    }

    boolean checkAvailable(Book book) {
        return book.available;
    }

    void lend(User user, Book book) {
        user.borrow = new Borrow(book);
        book.borrower = user;
        book.available = false;
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

class User {
    int id;
    Borrow borrow;

    void returnBook(Book book) {
        this.borrow = null;
        book.borrower = null;
        book.available = true;
    }

    void buy(Book book, Payment payment) {
        payment.pay(book.price);
    }
}

class Borrow {
    int id;
    Book book;
    public Borrow(Book book) {
        this.book = book;
    }
}

class Book {
    String isbn;
    String title;
    String subject;
    String author;
    String publisher;
    String language;
    boolean available;
    User borrower;
    int price;
}

interface Operator {
    void apply(Book book, List<Book> res, List<Filter> filters);
}

class And implements Operator {
    public void apply(Book book, List<Book> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (!filter.apply(book)) return;
        }
        res.add(book);
    }
}

class Or implements Operator {
    public void apply(Book book, List<Book> res, List<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.apply(book)) {
                res.add(book);
                return;
            }
        }
    }
}

interface Filter {
    boolean apply(Book book);
}

class TitleFilter implements Filter {
    String title;
    public TitleFilter(String title) {
        this.title = title;
    }
    public boolean apply(Book book) {
        return title.equals(book.title);
    }
}

class SubjectFilter implements Filter {
    String subject;
    public SubjectFilter(String subject) {
        this.subject = subject;
    }
    public boolean apply(Book book) {
        return subject.equals(book.subject);
    }
}

class AuthorFilter implements Filter {
    String author;
    public AuthorFilter(String author) {
        this.author = author;
    }
    public boolean apply(Book book) {
        return author.equals(book.author);
    }
}

class PublisherFilter implements Filter {
    String publisher;
    public PublisherFilter(String publisher) {
        this.publisher = publisher;
    }
    public boolean apply(Book book) {
        return publisher.equals(book.publisher);
    }
}

class LanguageFilter implements Filter {
    String language;
    public LanguageFilter(String language) {
        this.language = language;
    }
    public boolean apply(Book book) {
        return language.equals(book.language);
    }
}

