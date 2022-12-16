package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.client.ContainerThreadResourceClient;
import resourceater.model.resource.thread.container.ContainerThreadResource;
import resourceater.model.resource.thread.container.ContainerThreadResourceModel;
import resourceater.model.resource.thread.container.CreateContainerThreadResourceRequest;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static resourceater.controller.Mappings.CONTAINER_THREADS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CONTAINER_THREADS)
@ExposesResourceFor(ContainerThreadResourceModel.class)
@Tag(name = "Container Threads")
public class ContainerThreadResourceController extends ResourceController<CreateContainerThreadResourceRequest, ContainerThreadResource> {
    private final ContainerThreadResourceClient client;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ContainerThreadResourceController(
        PagedResourcesAssembler<ContainerThreadResource> pagedAssembler,
        ModelAssembler<ContainerThreadResource> modelAssembler,
        ResourceRepository<ContainerThreadResource> repository,
        ContainerThreadResourceClient client) {
        super(pagedAssembler, modelAssembler, repository);
        this.client = client;
    }

    @GetMapping("{id}/block")
    @ResponseStatus(NO_CONTENT)
    public void block(@PathVariable String id) {
        repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Unable to find resource: %s", id)))
            .block();
    }

    @Override
    ContainerThreadResource createResource(CreateContainerThreadResourceRequest request) {
        return new ContainerThreadResource(request, client);
    }
}
