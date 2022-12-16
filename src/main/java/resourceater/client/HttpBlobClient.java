package resourceater.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jonatan Ivanov
 */
@FeignClient(name = "httpBlob")
public interface HttpBlobClient {
    @GetMapping byte[] getBytes();
}
