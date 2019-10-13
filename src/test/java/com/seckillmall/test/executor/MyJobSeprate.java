package com.seckillmall.test.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;

public class MyJobSeprate {

    static int coreNum = 4;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //首先是不分多线程的素数任务计算
        long t1 = System.currentTimeMillis();
        List<Integer> one = getPrime(1, 200000);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);



        //然后是分片的任务计算
        ExecutorService executorService = Executors.newFixedThreadPool(coreNum);

        MyFuture m1 = new MyFuture(1,80000);
        MyFuture m2 = new MyFuture(80001,120000);
        MyFuture m3 = new MyFuture(120001,170000);
        MyFuture m4 = new MyFuture(170001,200000);

        Future<List<Integer>> f1 = executorService.submit(m1);
        Future<List<Integer>> f2 = executorService.submit(m2);
        Future<List<Integer>> f3 = executorService.submit(m3);
        Future<List<Integer>> f4 = executorService.submit(m4);

        long t3 = System.currentTimeMillis();
        List<Integer> res = new ArrayList<>();
        res.addAll(f1.get());
        res.addAll(f2.get());
        res.addAll(f3.get());
        res.addAll(f4.get());
        long t4 = System.currentTimeMillis();
        System.out.println(t4 - t3);

        executorService.shutdown();


    }

    static class MyFuture implements Callable<List<Integer>> {

        private int startPos;
        private int endPos;

        public MyFuture(int start, int end){
            this.startPos = start;
            this.endPos = end;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(startPos, endPos);
        }
    }

    //计算素数函数
    static boolean isPrime(int x){
        for(int i=2 ; i<=x/2 ; i++){
            if(x % i == 0){
                return false;
            }
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end){
        List<Integer> res = new ArrayList<>();
        for(int i = start; i<=end; i++){
            if(isPrime(i)){
                res.add(i);
            }
        }
        return res;
    }
}
