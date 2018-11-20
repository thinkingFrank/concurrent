package simple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by minghua.zmh on 2018/9/24.
 */
public class ThreadGroupAPISimple {

    public static void  main(String... arg) throws InterruptedException{
        ThreadGroup top = Thread.currentThread().getThreadGroup();
        System.out.print(top);
        ThreadGroup group = new ThreadGroup(top,"group");
        System.out.println(group.getParent().equals(top));
        Thread thread = new Thread(group,()->{
            while(true){
                try{
                    TimeUnit.MILLISECONDS.sleep(10L);
                    System.out.println("hello");
                }catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }
            }
        },"MyThread");
        thread.start();
        TimeUnit.MILLISECONDS.sleep(20L);

        Thread[] list = new Thread[top.activeCount()];
        int i = top.enumerate(list);
        int j = top.enumerate(list,false);
        System.out.print(i+"=="+j);
        top.list();
        group.interrupt();
    }
}
