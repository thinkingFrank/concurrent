package simple.pubsub.single;

import java.util.LinkedList;

/**
 * 线程间通信的时间机制
 *
 * notify and wait
 * 1. notify和wait的对象要一致,和对象的monitor有关
 * 2. notify和wait需要在syncnized环境中，monitor才会存在
 *
 * 注意wait和sleep的异同
 *
 * Created by minghua.zmh on 2018/9/4.
 */
public class EventQueueSingle {

    static class Event{

    }

    private final LinkedList<Event> events = new LinkedList<>();

    private final int max;

    private final static  int DEFAULT = 10;

    public EventQueueSingle(){
        this.max = EventQueueSingle.DEFAULT;
    }

    public EventQueueSingle(int maxLen){
        this.max = maxLen;
    }

    /**
     * 给队列添加事件
     * @param event
     */
    public synchronized void offer(Event event) throws InterruptedException{
            if (events.size() >= this.max) {
                try {
                    console("queue full wait");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console("queue add one");
            events.addLast(event);
            this.notify();
    }

    /**
     * 从队列弹出事件
     * @return Event
     */
    public synchronized Event take() throws InterruptedException{
            if (events.size() <= 0) {
                console("queue empty wait");
                this.wait();
            }
            console("queue remove one");
            Event event = events.removeFirst();
            this.notify();
            return event;
    }

    private void console(String msg){
        System.out.printf("%s:%s\n",Thread.currentThread().getName(),msg);
    }
}
