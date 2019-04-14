package resourceater.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.client.ContainerThreadResourceClient;
import resourceater.model.resource.thread.container.ContainerThreadResource;
import resourceater.model.resource.thread.container.ContainerThreadResourceRequest;
import resourceater.repository.ResourceRepository;

import java.util.NoSuchElementException;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/containerThreads")
public class ContainerThreadResourceController extends ResourceController<ContainerThreadResourceRequest, ContainerThreadResource> {
    private final ContainerThreadResourceClient client;

    public ContainerThreadResourceController(
        ResourceRepository<ContainerThreadResource> repository,
        ContainerThreadResourceClient client) {
        super(repository);
        this.client = client;
    }

    @GetMapping("{id}/block")
    public void block(@PathVariable String id) {
        repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(String.format("Unable to find ContainerThreadResource: %s", id)))
            .block();
    }

    @Override
    ContainerThreadResource createResource(ContainerThreadResourceRequest request) {
        return new ContainerThreadResource(request, client);
    }
}
