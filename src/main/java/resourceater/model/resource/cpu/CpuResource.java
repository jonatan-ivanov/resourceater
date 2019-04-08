package resourceater.model.resource.cpu;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Resource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class CpuResource implements Resource {
    @JsonIgnore private final ExecutorService executorService;
    @Getter private final long size;

    @JsonCreator public CpuResource(@JsonProperty("size") int size) {
        this.size = size;
        this.executorService = new ThreadPoolExecutor(size, size, 0, SECONDS, new ArrayBlockingQueue<>(1));
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
}
