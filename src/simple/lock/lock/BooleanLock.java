package simple.lock.lock;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.time.temporal.ChronoField.MICRO_OF_SECOND;

/**
 *
 * Created by minghua.zmh on 2018/9/24.
 */
public class BooleanLock implements Lock{
    //private AtomicBoolean locked= new AtomicBoolean(false);
    private Boolean locked = false;
    private List<Thread> blockingThreads = new LinkedList<>();
    private Thread currentThread;

    @Override
    public void lock() throws InterruptedException{
        synchronized (this){
            while(locked){
                if(!blockingThreads.contains(Thread.currentThread()))
                    blockingThreads.add(Thread.currentThread());
                System.out.println(Thread.currentThread()+" wait"+ LocalTime.now());
                this.wait();
            }
            blockingThreads.remove(Thread.currentThread());
            currentThread = Thread.currentThread();
            locked = true;
            System.out.println(Thread.currentThread()+" get lock"+ LocalTime.now());

        }
    }

    @Override
    public void lock(Long mills) throws InterruptedException,TimeoutException{
        synchronized (this){
            if(mills<=0){
                this.lock();
            }else{
                long remain = mills;
                long end = LocalTime.now(ZoneId.of("CTT")).getLong(MICRO_OF_SECOND)+mills;
                while(locked){
                    if(remain<=0){
                        throw new TimeoutException("out time");
                    }
                    if(!blockingThreads.contains(Thread.currentThread())){
                        blockingThreads.add(Thread.currentThread());
                    }
                    this.wait();
                    remain = end - LocalTime.now().getLong(MICRO_OF_SECOND);

                }
                blockingThreads.remove(Thread.currentThread());
                locked=true;
                currentThread = Thread.currentThread();
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this){
            if(Thread.currentThread().equals(currentThread)){
                locked=false;
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockingThreads() {
        return null;
    }
}
