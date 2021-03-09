package com.vipin.print123;


public class Print123UsingLambda {


    private volatile boolean print1 = true;
    private volatile boolean print2 = false;
    private volatile boolean print3 = false;


    public static void main(String[] args) {
        Object lockObject = new Object();
        Print123UsingLambda print123 = new Print123UsingLambda();
        Thread print1 = new Thread(print123.new Print1(lockObject), "Thread printing 1");
        Thread print2 = new Thread(print123.new Print2(lockObject), "Thread printing 2");
        Thread print3 = new Thread(print123.new Print3(lockObject), "Thread printing 3");

        print1.start();
        print2.start();
        print3.start();

    }

    class Print1 implements Runnable {
        private Object lockedObject;

        public Print1(Object lockedObject) {
            this.lockedObject = lockedObject;
        }

        @Override
        public void run() {

            while (true) {
                synchronized (this.lockedObject) {
                    try {
                        while (!print1)
                            this.lockedObject.wait();
                        System.out.print("1");
                        Thread.sleep(1000);
                        print2 = true;
                        print3 = false;
                        print1 = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.lockedObject.notifyAll();
                }

            }
        }

    }


    class Print2 implements Runnable {
        private Object lockedObject;

        public Print2(Object lockedObject) {
            this.lockedObject = lockedObject;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this.lockedObject) {
                    try {
                        while (!print2)
                            this.lockedObject.wait();
                        System.out.print("2");
                        Thread.sleep(1000);
                        print2 = false;
                        print3 = true;
                        print1 = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.lockedObject.notifyAll();
                }

            }
        }

    }

    class Print3 implements Runnable {
        private Object lockedObject;

        public Print3(Object lockedObject) {
            this.lockedObject = lockedObject;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this.lockedObject) {
                    try {
                        while (!print3)
                            this.lockedObject.wait();
                        System.out.println("3");
                        Thread.sleep(1000);
                        print2 = false;
                        print3 = false;
                        print1 = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.lockedObject.notifyAll();
                }

            }
        }
    }
}

