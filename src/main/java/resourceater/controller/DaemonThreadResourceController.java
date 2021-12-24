package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.thread.daemon.CreateDaemonThreadResourceRequest;
import resourceater.model.resource.thread.daemon.DaemonThreadResource;
import resourceater.model.resource.thread.daemon.DaemonThreadResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.THREAD_POOLS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(THREAD_POOLS)
@ExposesResourceFor(DaemonThreadResourceModel.class)
@Tag(name = "Threads")
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
