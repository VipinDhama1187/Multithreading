package com.vipin.waitNotify;

import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

class Consumer implements Runnable {
    private Queue queue;
    private int capacity;

    public Consumer(Queue queue,int capacity) {
        this.queue = queue;
        this.capacity = capacity;

    }

    @Override
    public void run() {
        while (true)
         consumer();
    }

    public void consumer() {
        synchronized (this.queue) {
            try {
                while (this.queue.isEmpty())
                    this.queue.wait();
                    double number  = (double) this.queue.poll();
                    System.out.println("Consumer consuming -->" + number);
                    Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.queue.notifyAll();
        }
    }
}

class Producer implements Runnable {
    private Queue queue;
    private int capacity;

    public Producer(Queue queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        while (true)
            produce(Math.random());
    }

    public void produce(double number) {
        synchronized (this.queue) {
            try {
                while (this.queue.size()==this.capacity)
                    this.queue.wait();
                    System.out.println("Producer producing -->" + number);
                    this.queue.add(number);
                    Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.queue.notifyAll();
        }
    }
}


public class InterThreadCommunication {

    public static void main(String[] args) {
        int capcity = 3;
        Queue<Double> queue = new LinkedBlockingQueue<Double>(capcity);
        Thread producer  =  new Thread(new Producer(queue,capcity));
        Thread consumer  =  new Thread(new Consumer(queue, capcity));
    producer.start();
    consumer.start();


    }
}
