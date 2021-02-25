package com.vipin.racecondition;

public class RaceConditionMain {
    public static void main(String[] args) {

        Counter counter = new Counter();

        Thread thread1 = new Thread(getRunnable(counter, "Thread 1 final Count...."));

        Thread thread2 = new Thread(getRunnable(counter, "Thread 2 final Count...."));

        thread1.start();
        thread2.start();
    }

    private static Runnable getRunnable(Counter counter, String message) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++)
                counter.incAndGet();
            System.out.println(message + " " + counter.getCount());
        };
    }

}
