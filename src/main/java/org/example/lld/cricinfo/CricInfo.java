package org.example.lld.cricinfo;

public class CricInfo {
}

abstract class Match {

}

class ODI extends Match {

}

class Test extends Match {

}

class T20 extends Match {

}

interface MatchFactory {
    Match create();
    void update(Match match);;
}

class ODIFactory implements MatchFactory {
    public Match create() {
        return new ODI();
    }
    public void update(Match match) {
        System.out.println("update ODI");
    }
}

class TestFactory implements MatchFactory {
    public Match create() {
        return new Test();
    }
    public void update(Match match) {
        System.out.println("update Test");
    }
}

class T20Factory implements MatchFactory {
    public Match create() {
        return new T20();
    }
    public void update(Match match) {
        System.out.println("update T20");
    }
}

class Admin {
    MatchFactory matchfactory;

    public void setMatchFactory(MatchFactory matchfactory) {
        this.matchfactory = matchfactory;
    }

    public Match createMatch() {
        return matchfactory.create();
    }

    public void updateMatch(Match match) {
        matchfactory.update(match);
    }
}

class AdminFactory {
    Admin create() {
        return new Admin();
    }
}

class Client {
    public static void main(String[] args) {
        AdminFactory adminfactory = new AdminFactory();
        Admin admin = adminfactory.create();
        admin.setMatchFactory(new ODIFactory());
        Match ODI = admin.createMatch();
        admin.updateMatch(ODI);
        admin.setMatchFactory(new TestFactory());
        Match Test = admin.createMatch();
        admin.updateMatch(Test);
        admin.setMatchFactory(new T20Factory());
        Match T20 = admin.createMatch();
        admin.updateMatch(T20);
    }
}