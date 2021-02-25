package com.vipin.racecondition;

public class Counter {

    private Long count = 0l;

    public Long incAndGet() {
        synchronized (this) {
            this.count++;
            return this.count;
        }

    }

    public Long getCount() {
        return count;
    }
}
