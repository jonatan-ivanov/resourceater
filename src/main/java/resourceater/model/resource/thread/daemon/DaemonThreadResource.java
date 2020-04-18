package resourceater.model.resource.thread.daemon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class DaemonThreadResource implements Resource<DaemonThreadResource> {
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final List<Thread> threads = new ArrayList<>();

    public DaemonThreadResource(CreateDaemonThreadResourceRequest request) {
        for (int i = 0; i < request.getSize(); i++) {
            Thread thread = new Thread(this::run, String.format("daemonThread-%s#%d", getId(), i));
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
    public void destroy() {
        countDownLatch.countDown();
    }

    @Override
    public Model<DaemonThreadResource> toModel() {
        return DaemonThreadResourceModel.builder()
            .id(this.getId())
            .size(this.threads.size())
            .build();
    }
}
