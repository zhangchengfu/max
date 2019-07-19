package com.laozhang.max_interview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程按顺序执行
 */
public class ThreadSeqence {
    public static void main(String[] args) {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread1 run ......");
            }
        }, "t1");
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join(); // 等待t1执行完毕
                    System.out.println("Thread2 run ......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");
        final Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join(); // 等待t2执行完毕
                    System.out.println("Thread3 run ......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t3");
        t2.start();
        t1.start();
        t3.start();

        final Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread4 run ......");
            }
        }, "t4");
        final Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread5 run ......");
            }
        }, "t5");
        final Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread6 run ......");
            }
        }, "t6");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(t4);
        executor.submit(t5);
        executor.submit(t6);
    }
}
