package simple.pubsub.multi;

import java.util.concurrent.TimeUnit;

import simple.pubsub.multi.EventQueueMulti.Event;

/**
 *
 * Created by minghua.zmh on 2018/9/16.
 */
public class EventClassMulti {
    public static void main(String... args) throws InterruptedException{
        EventQueueMulti eventQueue = new EventQueueMulti();
        new Thread(()->{
            while (true){
                try {
                    eventQueue.offer(new Event());
                    TimeUnit.MILLISECONDS.sleep(100L*(2L));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },"producer").start();
        for (Long i =0L;i<5;i++) {
            Long ii = i;
            new Thread(() -> {
                while (true) {
                    try {
                        eventQueue.take();
                        TimeUnit.MILLISECONDS.sleep(1000L*(ii+1L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
