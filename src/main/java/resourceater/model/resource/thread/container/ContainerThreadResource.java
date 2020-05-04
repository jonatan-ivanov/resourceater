package resourceater.model.resource.thread.container;

import static java.lang.String.format;

import feign.FeignException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import resourceater.client.ContainerThreadResourceClient;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class ContainerThreadResource extends Resource<ContainerThreadResource> {
    private final CountDownLatch containerThreadLatch = new CountDownLatch(1);
    private final List<Thread> threads = new ArrayList<>();

    private final int size;
    private final ContainerThreadResourceClient client;

    public ContainerThreadResource(CreateContainerThreadResourceRequest request, ContainerThreadResourceClient client) {
        this(request.getSize(), client, request.getTtl());
    }

    private ContainerThreadResource(int size, ContainerThreadResourceClient client, Duration ttl) {
        super(ttl);
        this.size = size;
        this.client = client;
    }

    private void run() {
        try {
            log.info(format("%s started", Thread.currentThread().getName()));
            client.block(this.getId());
        }
        catch (FeignException e) {
            log.warn(getMessage(e), e);
        }
        finally {
            // threads need to be released otherwise the container thread will be blocked
            containerThreadLatch.countDown();
            log.info(format("%s stopped", Thread.currentThread().getName()));
        }
    }

    // https://github.com/OpenFeign/feign/issues/912
    private String getMessage(FeignException exception) {
        return format("Unable to block container thread from %s: %s",
            Thread.currentThread().getName(),
            Optional.ofNullable(exception.contentUTF8()).orElse("")
        );
    }

    public void block() {
        try {
            containerThreadLatch.await();
        }
        catch (InterruptedException e) {
            log.warn(format("Thread: %s was interrupted", Thread.currentThread().getName()), e);
        }
    }

    @Override
    public void saved() {
        // we should start the threads after the resource is saved
        // otherwise the block HTTP call will not find the resource
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(this::run, format("containerCallerThread-%s#%d", getId(), i));
            threads.add(thread);
            thread.start();
        }
    }

    @Override
    public void destroy() {
        containerThreadLatch.countDown();
    }

    @Override
    public Model<ContainerThreadResource> toModel() {
        return new ContainerThreadResourceModel(this, this.threads.size());
    }
}
