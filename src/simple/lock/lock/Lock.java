package simple.lock.lock;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by minghua.zmh on 2018/9/24.
 */
public interface Lock {
    void lock() throws InterruptedException;
    void lock(Long delay) throws InterruptedException,TimeoutException;
    void unlock();
    List<Thread> getBlockingThreads();
}
