package simple;

import java.util.concurrent.TimeUnit;

/**
 * Created by minghua.zmh on 2018/9/2.
 */
public class ThreadAPISimple {
    public static void main(String... args){
        try {
            new ThreadAPISimple().start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void start() throws InterruptedException{
        /**
         * 通过一个Runnable实例，来初始多个线程，使得线程共享数据
         */
        ThreadTwo threadRunnable = new ThreadTwo();
        //线程的分组
        ThreadGroup g = new ThreadGroup(Thread.currentThread().getThreadGroup(),"test");
        Thread t1 = new Thread(g,threadRunnable,"THREAD_NAME_1");
        Thread t2 = new Thread(g,threadRunnable,"THREAD_NAME_2");
        Thread t3 = new Thread(threadRunnable,"THREAD_NAME_3");
        Thread t4 = new Thread(threadRunnable,"THREAD_NAME_4");
        t1.setPriority(10);
        t2.setPriority(10);
        t3.setPriority(10);
        t4.setPriority(5);
        //如果没有非守护进程存在，jdk结束进程
        //t1.setDaemon(true);
        //t2.setDaemon(true);
        //t3.setDaemon(true);
        //t4.setDaemon(true);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        //新启动一个线程去打断，线程1的中断。使得线程1，快速处理，同时会抛出中断异常
        Thread t5 = new Thread(()->{while(true){t1.interrupt();}});
        t5.setDaemon(true);
        t5.start();

        System.out.println("Not Block"+threadRunnable.getIndex());

        //阻塞4个线程等待线程执行完成
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.println("Block"+threadRunnable.getIndex());

    }


    public class ThreadTwo implements Runnable{
        public synchronized int getIndex() {
            return index;
        }

        public synchronized void setIndex(int index) {
            this.index = index;
        }

        private int index = 0;
        public void run() {

            while(index<=100){
                Thread current = Thread.currentThread();
                System.out.println(current.getThreadGroup().getName()+current.getName()+"\t"+current.getPriority()+"，This is Runnable " + index);
                //线程是否转入Runnable状态取决于服务器资源
                Thread.yield();
                index++;
                try {
                    TimeUnit.MILLISECONDS.sleep(1000L);
                } catch (InterruptedException e) {//打断Blocking状态，会抛出中断异常

                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
