package resourceater.model.resource.cpu;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class CpuResource extends Resource<CpuResource> {
    private final ExecutorService executorService;
    private final int size;

    public CpuResource(CreateCpuResourceRequest request) {
        super(request.ttl());
        this.size = request.size();
        this.executorService = new ThreadPoolExecutor(this.size, this.size, 0, SECONDS, new ArrayBlockingQueue<>(1));
        for (int i = 0; i < size; i++) {
            executorService.submit(this::run);
        }
    }

    private void run() {
        log.info(String.format("%s started", Thread.currentThread().getName()));
        for (;;) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
        log.info(String.format("%s stopped", Thread.currentThread().getName()));
    }

    @Override
    public void destroy() {
        executorService.shutdownNow();
    }

    @Override
    public Model<CpuResource> toModel() {
        return new CpuResourceModel(this, this.size);
    }
}
