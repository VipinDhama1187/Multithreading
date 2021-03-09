package com.vipin.deadlock.prevention;

public class ThreadDeadLockPrevention {

    public static void main(String[] args) {
        Object objectA = new Object();
        Object objectB = new Object();

        ThreadDeadLockPrevention theThreadDeadLock = new ThreadDeadLockPrevention();
        Thread threadA = new Thread(theThreadDeadLock.new ThreadA(objectA, objectB), "Thread-A");
        Thread threadB = new Thread(theThreadDeadLock.new ThreadB(objectA, objectB), "Thread-B");

        threadA.start();
        threadB.start();
    }

    class ThreadB implements Runnable{

        private Object objectA;
        private Object objectB;

        public ThreadB(Object objectA, Object objectB) {
            this.objectA = objectA;
            this.objectB = objectB;
        }

        @Override
        public void run() {
            synchronized (this.objectA) {
                try {
                    System.out.println(Thread.currentThread().getName()+" Accessing lock on resource A");
                    Thread.sleep(1000);

                    synchronized (this.objectB) {
                        System.out.println(Thread.currentThread().getName()+" Accessing lock on resource B");
                        Thread.sleep(1000);
                        this.objectB.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.objectA.notifyAll();
            }

        }
    }

    class ThreadA implements Runnable {

        private Object objectA;
        private Object objectB;

        public ThreadA(Object objectA, Object objectB) {
            this.objectA = objectA;
            this.objectB = objectB;
        }

        @Override
        public void run() {
            synchronized (this.objectA) {
                try {

                    System.out.println(Thread.currentThread().getName()+" Accessing lock on resource A");
                    Thread.sleep(1000);

                    //this.objectA.wait();

                    synchronized (this.objectB) {
                        System.out.println(Thread.currentThread().getName()+" Accessing lock on resource B");
                        Thread.sleep(1000);
                        this.objectB.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.objectA.notifyAll();
            }

        }
    }
}
