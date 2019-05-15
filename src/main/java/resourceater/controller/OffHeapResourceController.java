package resourceater.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.offheap.OffHeapResource;
import resourceater.model.resource.offheap.OffHeapResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.DIRECT_BUFFERS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(DIRECT_BUFFERS)
@Api(tags = {"Off-Heap Direct Buffers"})
public class OffHeapResourceController extends ResourceController<OffHeapResourceRequest, OffHeapResource> {
    public OffHeapResourceController(ResourceRepository<OffHeapResource> repository) {
        super(repository);
    }

    @Override
    OffHeapResource createResource(OffHeapResourceRequest request) {
        return new OffHeapResource(request);
    }
}
