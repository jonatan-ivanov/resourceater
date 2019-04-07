package resourceater.model.resource.thread;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class ThreadResource implements Resource {
    @JsonIgnore private final CountDownLatch countDownLatch = new CountDownLatch(1);
    @JsonIgnore private final List<Thread> threads = new ArrayList<>();

    @JsonCreator public ThreadResource(@JsonProperty("size") int size) {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(this::run, String.format("threadResource-%s#%d", getId(), i));
            threads.add(thread);
            thread.start();
        }
    }

    private void run() {
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            log.warn(String.format("Thread: %s was interrupted", Thread.currentThread().getName()), e);
        }
    }

    @Override
    public long getSize() {
        return threads.size();
    }

    @Override
    public void destroy() {
        countDownLatch.countDown();
    }
}
