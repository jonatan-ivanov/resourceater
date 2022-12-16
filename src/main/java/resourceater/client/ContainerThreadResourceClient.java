package resourceater.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jonatan Ivanov
 */
@FeignClient(name = "containerThreadResource")
public interface ContainerThreadResourceClient {
    @GetMapping("{id}/block")
    void block(@PathVariable("id") String id);
}
