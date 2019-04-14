package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.ThreadResource;
import resourceater.model.resource.thread.ThreadResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/threadPools")
public class ThreadResourceController extends ResourceController<ThreadResourceRequest, ThreadResource> {
    public ThreadResourceController(ResourceRepository<ThreadResource> repository) {
        super(repository);
    }

    @Override
    ThreadResource createResource(ThreadResourceRequest request) {
        return new ThreadResource(request);
    }
}
