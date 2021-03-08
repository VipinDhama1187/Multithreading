package com.vipin.print123;

public class Print123 implements Runnable{
    private static Object lockObject  = new Object();
    private volatile boolean print1 = true;
    private volatile boolean print2 = false;
    private volatile boolean print3 = false;

    public Print123(Object lockObject) {
        this.lockObject = lockObject;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.lockObject) {
                try {
                    while (!this.print1)
                        this.lockObject.wait();
                    System.out.println("1");
                    Thread.sleep(1000);
                    this.print2 = true;
                    this.print1 = false;
                    this.print3 = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.lockObject.notifyAll();
            }

            synchronized (this.lockObject) {
                try {
                    while (!this.print2)
                        this.lockObject.wait();
                    System.out.println("2");
                    Thread.sleep(1000);
                    this.print2 = false;
                    this.print1 = false;
                    this.print3 = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.lockObject.notifyAll();
            }

            synchronized (this.lockObject) {
                try {
                    while (!this.print3)
                        this.lockObject.wait();
                    System.out.println("3");
                    Thread.sleep(1000);
                    this.print2 = false;
                    this.print1 = true;
                    this.print3 = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.lockObject.notifyAll();
            }
        }
    }
    public static void main(String[] args) {
        lockObject = new Object();
        Thread print123_1 = new Thread(new Print123(lockObject));
        Thread print123_2 = new Thread(new Print123(lockObject));
        Thread print123_3 = new Thread(new Print123(lockObject));

        print123_1.start();
        print123_2.start();
        print123_3.start();
    }
}
