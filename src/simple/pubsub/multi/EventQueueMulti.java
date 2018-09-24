package simple.pubsub.multi;

import java.util.LinkedList;

/**
 * 线程间通信的时间机制
 *
 * 避免多消费者不一致的问题，注意notify_all()的作用
 *
 * wait_set的概念和特性，要注意
 *
 * synchronized的缺陷
 * 1. 不可中断
 * 2. 无法控制阻塞时间
 *
 * Created by minghua.zmh on 2018/9/4.
 */
public class EventQueueMulti {

    static class Event{
    }

    private final LinkedList<Event> events = new LinkedList<>();

    private final int max;

    private final static  int DEFAULT = 10;

    public EventQueueMulti(){
        this.max = EventQueueMulti.DEFAULT;
    }

    public EventQueueMulti(int maxLen){
        this.max = maxLen;
    }

    /**
     * 给队列添加事件
     * @param event
     */
    public synchronized void offer(Event event) throws InterruptedException{
            while (events.size() >= this.max) {
                try {
                    console("queue full wait");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console("queue add one");
            events.addLast(event);
            notifyAll();
    }

    /**
     * 从队列弹出事件
     * @return Event
     */
    public synchronized Event take() throws InterruptedException{
            while (events.size() <= 0) {
                console("queue empty wait");
                this.wait();
            }
            console("queue remove one");
            Event event = events.removeFirst();
            notifyAll();
            return event;
    }

    private void console(String msg){
        System.out.printf("%s:%s\n",Thread.currentThread().getName(),msg);
    }
}
