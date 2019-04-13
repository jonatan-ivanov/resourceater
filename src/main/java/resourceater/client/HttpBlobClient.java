package resourceater.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jonatan Ivanov
 */
@FeignClient(name = "httpBlob", url = "${feign.client.config.httpBlob.url}")
public interface HttpBlobClient {
    @GetMapping byte[] getBytes();
}
