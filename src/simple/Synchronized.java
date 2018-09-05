package simple;

import java.util.concurrent.TimeUnit;


/**
 * Synchroized关键字
 * Created by minghua.zmh on 2018/9/4.
 */
public class Synchronized implements Runnable{
    private int index = 1;
    private final static int MAX = 500;
    //MUTEX 作为同步对象，不能为null
    private final static Object MUTEX = new Object();
    public static void main(String... args){
        final Synchronized task = new Synchronized();
        //使用同一个锁对象,t4不受影响
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");
        Thread t3 = new Thread(task,"t3");
        Thread t4 = new Thread(new Synchronized() ,"t4");
        t4.start();
        Thread t5 = new Thread(() -> {
            try {
            while (true) {
                System.out.println(Thread.currentThread().getName()+"hhh");
                Thread.sleep(1L);
            }
            }catch(Exception e){


            }
        }, "t5");
        t5.setDaemon(true);
        t5.start();

        t1.start();
        System.out.println("a");
        t2.start();
        System.out.println("b");
        t3.start();
        System.out.println("c");
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.interrupt();
        }catch (Exception e){

        }

        new Thread(Synchronized::m1,"aaa").start();
        new Thread(Synchronized::m2,"bbb").start();
    }

    @Override
    public void run(){

        while (index < MAX){
            synchronized (MUTEX) {
                System.out.println(Thread.currentThread().getName() + ":" + index++);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1L);
            }catch (InterruptedException e){

            }

        }

    }

    public static synchronized void m1(){
        try {
            System.out.println(Thread.currentThread().getName() );
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() );
        }catch (InterruptedException e){

        }
    }
    public static synchronized void m2(){
        try {
            System.out.println(Thread.currentThread().getName() );
            Thread.sleep(1000L);
        }catch (InterruptedException e){

        }
    }

}
