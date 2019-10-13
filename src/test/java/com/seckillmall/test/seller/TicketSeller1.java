package com.seckillmall.test.seller;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class TicketSeller1 {

    //static List<String> tickets = new ArrayList<>();

//    static {
//        for(int i=0; i<10000; i++) tickets.add("票编号：" + i);
//    }
//
//    public static void main(String[] args) {
//        for(int i=0; i<10; i++) {
//            new Thread(()->{
//                while(tickets.size() > 0) {
//                    System.out.println("线程-"+Thread.currentThread().getName()+"销售了--" + tickets.remove(0));
//                }
//            }).start();
//        }
//    }

//    static Vector<String> tickets = new Vector<>();
//
//    static {
//        for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
//    }
//
//    public static void main(String[] args) {
//
//        for(int i=0; i<10; i++) {
//            new Thread(()->{
//                while(tickets.size() > 0) {
//
////                    try {
////                        TimeUnit.MILLISECONDS.sleep(10);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    //这里一旦打断，判断和操作这两个原子操作之间的空隙产生线程问题
//
//                    System.out.println("销售了--" + tickets.remove(0));
//                }
//            }).start();
//        }
//    }

//    static List<String> tickets = new LinkedList<>();
//
//
//    static {
//        for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
//    }
//
//    public static void main(String[] args) {
//
//        for(int i=0; i<10; i++) {
//            new Thread(()->{
//                while(true) {
//                    synchronized(tickets) {
//                        if(tickets.size() <= 0) break;
//
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        System.out.println("销售了--" + tickets.remove(0));
//                    }
//                }
//            }).start();
//        }
//    }

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();


    static {
        for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
    }

    public static void main(String[] args) {

        for(int i=0; i<10; i++) {
            new Thread(()->{
                while(true) {
                    String s = tickets.poll();
                    if(s == null) break;
                    else System.out.println("销售了--" + s);
                }
            }).start();
        }
    }
}
