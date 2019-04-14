package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.heap.HeapResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/objects")
public class HeapResourceController extends ResourceController<HeapResourceRequest, HeapResource> {
    public HeapResourceController(ResourceRepository<HeapResource> repository) {
        super(repository);
    }

    @Override
    HeapResource createResource(HeapResourceRequest request) {
        return new HeapResource(request);
    }
}
