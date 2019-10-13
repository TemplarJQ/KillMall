package com.seckillmall.test.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class AQS_01 {

    static  int m = 0;

    public static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

//        long t1 = System.currentTimeMillis();
//        fun1();
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2 -  t1);
//
//        long t3 = System.currentTimeMillis();
//        func2();
//        long t4 = System.currentTimeMillis();
//        System.out.println(t4 - t3);
        func2();


    }

    public static void fun1() throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Thread(() -> {
                synchronized (AQS_01.class){
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();   //等待所有线程运行结束
        }

        System.out.println(m);    //代码输出随机
    }

    public static void func2() throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Thread(() -> {
                try {
                    lock.lock();
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
                } finally {
                    lock.unlock();
                }

            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();   //等待所有线程运行结束
        }

        System.out.println(m);    //代码输出随机
    }

}
