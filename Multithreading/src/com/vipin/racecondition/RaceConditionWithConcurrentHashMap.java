package com.vipin.racecondition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RaceConditionWithConcurrentHashMap {
    public static void main(String[] args) {

        // Counter counter = new Counter();

        Map<String, String> sharedMap = new ConcurrentHashMap<>();

        Thread thread1 = new Thread(getRunnable(sharedMap));

        Thread thread2 = new Thread(getRunnable(sharedMap));

        thread1.start();
        thread2.start();
    }

    private static Runnable getRunnable(Map<String, String> sharedMap) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++)
                synchronized (sharedMap) {
                    if (sharedMap.containsKey("key")) {
                        String value = sharedMap.remove("key");
                        if (null == value) {
                            System.out.println("Iteration :" + i + " Value for key is NULL");
                        }
                    } else {
                        sharedMap.put("key", "value");
                    }
                }
        };
    }

}
