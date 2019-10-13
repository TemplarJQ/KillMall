package com.seckillmall.test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class testPro<T> {
    final private List<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void addItem(T t){
        while(list.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();
    }

    public synchronized T getItem(){
        T t = null;
        while(list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.get(0);
        count --;
        this.notifyAll(); //通知生产者进行生产
        return t;
    }

    public static void main(String[] args) {
        testPro<String> c = new testPro<>();
        //启动消费者线程
        for(int i=0; i<10; i++) {
            new Thread(()->{
                for(int j=0; j<5; j++) System.out.println(c.getItem());
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动生产者线程
        for(int i=0; i<2; i++) {
            new Thread(()->{
                for(int j=0; j<25; j++) c.addItem(Thread.currentThread().getName() + " " + j);
            }, "p" + i).start();
        }
    }

}
