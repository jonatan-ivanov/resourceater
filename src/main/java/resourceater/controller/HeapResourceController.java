package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.heap.CreateHeapResourceRequest;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.heap.HeapResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.OBJECTS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(OBJECTS)
@ExposesResourceFor(HeapResourceModel.class)
@Tag(name = "Heap Objects")
public class HeapResourceController extends ResourceController<CreateHeapResourceRequest, HeapResource> {
    public HeapResourceController(
        PagedResourcesAssembler<HeapResource> pagedAssembler,
        ModelAssembler<HeapResource> modelAssembler,
        ResourceRepository<HeapResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    HeapResource createResource(CreateHeapResourceRequest request) {
        return new HeapResource(request);
    }
}
