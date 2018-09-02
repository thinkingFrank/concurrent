package simple;

import java.util.Timer;

/**
 * Created by minghua.zmh on 2018/9/2.
 */
public class ThreadSimple {
    public static void main(String... args){
        new ThreadSimple().start();


    }

    public void start(){
        /**
         * start 不会阻塞，只是让线程转入RUNNABLE状态
         */
        new ThreadOne().start();
        new Thread(new ThreadTwo()).start();
    }




    public class ThreadOne extends Thread{
        @Override
        public void run()  {
            for(int i=0;i<300;i++) {
                System.out.println("This is ThreadOne:" + i);
                try {
                    Thread.sleep(1000L);
                }catch (Exception e){

                }

            }
        }
    }

    public class ThreadTwo implements Runnable{
        public void run() {
            for (int i = 0; i < 300; i++){
                System.out.println("This is ThreadTwo" + i);
                try {
                    /**
                     * 让两个线程可以交替输出
                     */
                    Thread.sleep(1000L);
                } catch (Exception e) {

                }
            }
        }

    }
}
