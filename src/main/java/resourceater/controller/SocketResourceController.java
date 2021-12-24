package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.network.socket.CreateSocketResourceRequest;
import resourceater.model.resource.network.socket.SocketResource;
import resourceater.model.resource.network.socket.SocketResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.SOCKET_POOLS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(SOCKET_POOLS)
@ExposesResourceFor(SocketResourceModel.class)
@Tag(name = "Sockets")
public class SocketResourceController extends ResourceController<CreateSocketResourceRequest, SocketResource> {
    public SocketResourceController(
        PagedResourcesAssembler<SocketResource> pagedAssembler,
        ModelAssembler<SocketResource> modelAssembler,
        ResourceRepository<SocketResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    SocketResource createResource(CreateSocketResourceRequest request) {
        return new SocketResource(request);
    }
}
