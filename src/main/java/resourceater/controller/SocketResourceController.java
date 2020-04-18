package resourceater.controller;

import static resourceater.controller.Mappings.SOCKET_POOLS;

import io.swagger.annotations.Api;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.network.socket.CreateSocketResourceRequest;
import resourceater.model.resource.network.socket.SocketResource;
import resourceater.model.resource.network.socket.SocketResourceModel;
import resourceater.repository.ResourceRepository;

@RestController
@RequestMapping(SOCKET_POOLS)
@ExposesResourceFor(SocketResourceModel.class)
@Api(tags = {"Sockets"})
public class SocketResourceController extends ResourceController<CreateSocketResourceRequest, SocketResource> {
    public SocketResourceController(ModelAssembler<SocketResource> modelAssembler, ResourceRepository<SocketResource> repository) {
        super(modelAssembler, repository);
    }

    @Override
    SocketResource createResource(CreateSocketResourceRequest request) {
        return new SocketResource(request);
    }
}
