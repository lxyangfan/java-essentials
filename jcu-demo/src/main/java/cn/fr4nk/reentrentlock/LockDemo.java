package cn.fr4nk.reentrentlock;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        demo();
    }

    public void konck() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " knocks ... ");
            kick();
        } finally {
            lock.unlock();
        }
    }

    public void kick() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " kicks ... ");
        } finally {
            lock.unlock();
        }
    }

    public synchronized void knocks2() {
        System.out.println("knocks2()");
        kicks2();
    }

    public synchronized void kicks2() {
        System.out.println("kicks2()");
    }


    public static void demo() {
        final LockDemo lockDemo = new LockDemo();

        Runnable r = () -> {
            lockDemo.konck();
        };

        Thread sub = new Thread(r, "sub-thread");
        sub.start();

        try {
            Thread.currentThread().sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lockDemo.konck();

    }

}
