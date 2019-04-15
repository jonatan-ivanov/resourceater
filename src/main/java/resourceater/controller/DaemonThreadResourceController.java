package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.daemon.DaemonThreadResource;
import resourceater.model.resource.thread.daemon.DaemonThreadResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.THREAD_POOLS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/" + THREAD_POOLS)
public class DaemonThreadResourceController extends ResourceController<DaemonThreadResourceRequest, DaemonThreadResource> {
    public DaemonThreadResourceController(ResourceRepository<DaemonThreadResource> repository) {
        super(repository);
    }

    @Override
    DaemonThreadResource createResource(DaemonThreadResourceRequest request) {
        return new DaemonThreadResource(request);
    }

    @Override
    String getRel() {
        return THREAD_POOLS;
    }
}
