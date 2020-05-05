package resourceater.controller;

import static resourceater.controller.Mappings.THREAD_POOLS;

import io.swagger.annotations.Api;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.daemon.CreateDaemonThreadResourceRequest;
import resourceater.model.resource.thread.daemon.DaemonThreadResource;
import resourceater.model.resource.thread.daemon.DaemonThreadResourceModel;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(THREAD_POOLS)
@ExposesResourceFor(DaemonThreadResourceModel.class)
@Api(tags = {"Threads"})
public class DaemonThreadResourceController extends ResourceController<CreateDaemonThreadResourceRequest, DaemonThreadResource> {
    public DaemonThreadResourceController(
        PagedResourcesAssembler<DaemonThreadResource> pagedAssembler,
        ModelAssembler<DaemonThreadResource> modelAssembler,
        ResourceRepository<DaemonThreadResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    DaemonThreadResource createResource(CreateDaemonThreadResourceRequest request) {
        return new DaemonThreadResource(request);
    }
}
