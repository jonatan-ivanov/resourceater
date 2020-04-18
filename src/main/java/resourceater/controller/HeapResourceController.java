package resourceater.controller;

import static resourceater.controller.Mappings.OBJECTS;

import io.swagger.annotations.Api;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.heap.CreateHeapResourceRequest;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.heap.HeapResourceModel;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(OBJECTS)
@ExposesResourceFor(HeapResourceModel.class)
@Api(tags = {"Heap Objects"})
public class HeapResourceController extends ResourceController<CreateHeapResourceRequest, HeapResource> {
    public HeapResourceController(ModelAssembler<HeapResource> modelAssembler, ResourceRepository<HeapResource> repository) {
        super(modelAssembler, repository);
    }

    @Override
    HeapResource createResource(CreateHeapResourceRequest request) {
        return new HeapResource(request);
    }
}
