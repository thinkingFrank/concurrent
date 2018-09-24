package simple.pubsub.single;

import java.util.concurrent.TimeUnit;

import simple.pubsub.single.EventQueueSingle.Event;

/**
 * Created by minghua.zmh on 2018/9/16.
 */
public class EventClassSingle {
    public static void main(String... args) throws InterruptedException{
        EventQueueSingle eventQueue = new EventQueueSingle();
        new Thread(()->{
            while (true){
                try {
                    eventQueue.offer(new Event());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },"producer").start();

        new Thread(()->{
            while(true){
                try{
                    eventQueue.take();
                    TimeUnit.MILLISECONDS.sleep(100L);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
