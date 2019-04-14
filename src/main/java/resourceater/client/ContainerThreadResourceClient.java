package resourceater.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "containerThreadResource", url = "${feign.client.config.containerThreadResource.url}")
public interface ContainerThreadResourceClient {
    @GetMapping("{id}/block")
    void block(@PathVariable String id);
}
