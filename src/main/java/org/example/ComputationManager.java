package org.example;

import java.util.HashMap;
import java.util.Map;

public class ComputationManager {
    private boolean isReady = false; // monitor flag to control execution
    private int x = -1;
    private final Map<Thread, Number> results = new HashMap<>();

    public ComputationManager(int x) {
        this.x = x;
    }

    public void addComponent(String symbol) {
        isReady = false;
        Thread t = null;
        if (symbol.equals("s")) {
            t = new Thread(() -> {
                try {
                    Number result = square();
                    synchronized (results) {
                        results.put(Thread.currentThread(), result);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else if (symbol.equals("r")) {
            t = new Thread(() -> {
                try {
                    Number result = sqrt();
                    synchronized (results) {
                        results.put(Thread.currentThread(), result);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Invalid symbol");
        }

        if (t != null) {
            t.start();
        }
    }

    public Number square() throws InterruptedException {
        synchronized (this) {
            while (!isReady) {
                wait();
            }
        }
        System.out.println("Computing square on " + Thread.currentThread().getName());
        Thread.sleep(1000);
        return x * x;
    }

    public Number sqrt() throws InterruptedException {
        synchronized (this) {
            while (!isReady) {
                wait();
            }
        }
        System.out.println("Computing sqrt on " + Thread.currentThread().getName());
        Thread.sleep(1000);
        return Math.sqrt(x);
    }

    public void run() {
        synchronized (this) {
            isReady = true;
            notifyAll();
        }
    }

    public void summary() {
        synchronized (results) {
            results.forEach((thread, result) -> {
                try {
                    thread.join();
                    System.out.println(thread.getName() + " computed: " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}