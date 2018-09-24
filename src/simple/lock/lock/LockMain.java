package simple.lock.lock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Created by minghua.zmh on 2018/9/24.
 */
public class LockMain {
    private final Lock lock = new BooleanLock();

    public void  syncMethod(){

        try {
            lock.lock();
        }catch (InterruptedException e) {
        }
        try{
           //int random =  ThreadLocalRandom.current().nextInt(1000);

           Thread.sleep(1000L);
            System.out.println(Thread.currentThread()+" do sth"+ LocalTime.now());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args){
        LockMain main = new LockMain();
        IntStream.range(0,10).mapToObj(i->new Thread(main::syncMethod)).forEach(Thread::start);
    }
}
