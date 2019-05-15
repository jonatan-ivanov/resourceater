package resourceater.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import resourceater.client.ContainerThreadResourceClient;
import resourceater.model.resource.thread.container.ContainerThreadResource;
import resourceater.model.resource.thread.container.ContainerThreadResourceRequest;
import resourceater.repository.ResourceRepository;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static resourceater.controller.Mappings.CONTAINER_THREADS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CONTAINER_THREADS)
@Api(tags = {"Container Threads"})
public class ContainerThreadResourceController extends ResourceController<ContainerThreadResourceRequest, ContainerThreadResource> {
    private final ContainerThreadResourceClient client;

    public ContainerThreadResourceController(
        ResourceRepository<ContainerThreadResource> repository,
        ContainerThreadResourceClient client) {
        super(repository);
        this.client = client;
    }

    @GetMapping("{id}/block")
    public ResponseEntity<?> block(@PathVariable String id) {
        repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Unable to find resource: %s", id)))
            .block();

        return ResponseEntity.noContent().build();
    }

    @Override
    ContainerThreadResource createResource(ContainerThreadResourceRequest request) {
        return new ContainerThreadResource(request, client);
    }
}
