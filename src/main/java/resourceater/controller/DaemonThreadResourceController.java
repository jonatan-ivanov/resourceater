package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.daemon.DaemonThreadResource;
import resourceater.model.resource.thread.daemon.DaemonThreadResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/threadPools")
public class DaemonThreadResourceController extends ResourceController<DaemonThreadResourceRequest, DaemonThreadResource> {
    public DaemonThreadResourceController(ResourceRepository<DaemonThreadResource> repository) {
        super(repository);
    }

    @Override
    DaemonThreadResource createResource(DaemonThreadResourceRequest request) {
        return new DaemonThreadResource(request);
    }
}
