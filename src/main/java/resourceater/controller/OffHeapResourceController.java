package resourceater.controller;

import static resourceater.controller.Mappings.DIRECT_BUFFERS;

import io.swagger.annotations.Api;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.offheap.CreateOffHeapResourceRequest;
import resourceater.model.resource.offheap.OffHeapResource;
import resourceater.model.resource.offheap.OffHeapResourceModel;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(DIRECT_BUFFERS)
@ExposesResourceFor(OffHeapResourceModel.class)
@Api(tags = {"Off-Heap Direct Buffers"})
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
