package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.offheap.CreateOffHeapResourceRequest;
import resourceater.model.resource.offheap.OffHeapResource;
import resourceater.model.resource.offheap.OffHeapResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.DIRECT_BUFFERS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(DIRECT_BUFFERS)
@ExposesResourceFor(OffHeapResourceModel.class)
@Tag(name = "Off-Heap Direct Buffers")
public class OffHeapResourceController extends ResourceController<CreateOffHeapResourceRequest, OffHeapResource> {
    public OffHeapResourceController(
        PagedResourcesAssembler<OffHeapResource> pagedAssembler,
        ModelAssembler<OffHeapResource> modelAssembler,
        ResourceRepository<OffHeapResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    OffHeapResource createResource(CreateOffHeapResourceRequest request) {
        return new OffHeapResource(request);
    }
}
