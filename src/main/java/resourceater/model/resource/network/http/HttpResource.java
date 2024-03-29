package resourceater.model.resource.network.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import resourceater.client.HttpBlobClient;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class HttpResource extends Resource<HttpResource> {
    private final ExecutorService executorService;
    private final HttpBlobClient client;
    private final String url;

    public HttpResource(CreateHttpResourceRequest createHttpResourceRequest, HttpBlobClient client, String url) {
        super(createHttpResourceRequest.ttl());
        this.executorService = new ThreadPoolExecutor(1, 1, 0, SECONDS, new ArrayBlockingQueue<>(1));
        this.client = client;
        this.url = url;

        executorService.submit(this::run);
    }

    private void run() {
        log.info(String.format("%s started", Thread.currentThread().getName()));
        for (;;) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            else {
                client.getBytes(); // this will not be interrupted
            }
        }
        log.info(String.format("%s stopped", Thread.currentThread().getName()));
    }

    @Override
    public void destroy() {
        executorService.shutdownNow();
    }

    @Override
    public Model<HttpResource> toModel() {
        return new HttpResourceModel(this, this.url);
    }
}
