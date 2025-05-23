package org.example;

import java.util.List;

public class NestedInteger {
    Integer i;
    List<NestedInteger> l;
    boolean isInteger;
    public NestedInteger() {}
    public NestedInteger(int i) {
        this.i = i;
    }
    boolean isInteger() {
        return isInteger;
    }
    void setInteger(int i) {
        this.i = i;
    }
    Integer getInteger() {
        return i;
    }
    void add(NestedInteger ni) {
        l.add(ni);
    }
    List<NestedInteger> getList() {
        return l;
    }
}
