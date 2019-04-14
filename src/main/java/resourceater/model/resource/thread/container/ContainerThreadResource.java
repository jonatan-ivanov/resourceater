package resourceater.model.resource.thread.container;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import resourceater.client.ContainerThreadResourceClient;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static java.lang.String.format;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class ContainerThreadResource implements Resource {
    private final CountDownLatch initLatch = new CountDownLatch(1);
    private final CountDownLatch containerThreadLatch = new CountDownLatch(1);
    private final List<Thread> threads = new ArrayList<>();
    private final ContainerThreadResourceClient client;

    public ContainerThreadResource(ContainerThreadResourceRequest request, ContainerThreadResourceClient client) {
        this.client = client;
        for (int i = 0; i < request.getSize(); i++) {
            Thread thread = new Thread(this::run, format("containerCallerThread-%s#%d", getId(), i));
            threads.add(thread);
            thread.start();
        }
    }

    private void run() {
        try {
            // the thread should wait until the controller saves the resource
            // otherwise the block HTTP call will not find the resource
            initLatch.await();
            log.info(format("%s started", Thread.currentThread().getName()));
            client.block(this.getId());
        }
        catch (InterruptedException e) {
            log.warn(format("Thread: %s was interrupted", Thread.currentThread().getName()), e);
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
            Optional.ofNullable(exception.content()).map(String::new).orElse("")
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
    public Resource init() {
        initLatch.countDown();
        return this;
    }

    @Override
    public void destroy() {
        containerThreadLatch.countDown();
    }

    @Override
    public Response toResponse() {
        return ContainerThreadResourceResponse.builder()
            .resourceId(this.getId())
            .size(this.threads.size())
            .build();
    }
}
