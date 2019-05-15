package resourceater.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.network.socket.SocketResource;
import resourceater.model.resource.network.socket.SocketResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.SOCKET_POOLS;

@RestController
@RequestMapping(SOCKET_POOLS)
@Api(tags = {"Sockets"})
public class SocketResourceController extends ResourceController<SocketResourceRequest, SocketResource> {
    public SocketResourceController(ResourceRepository<SocketResource> repository) {
        super(repository);
    }

    @Override
    SocketResource createResource(SocketResourceRequest request) {
        return new SocketResource(request);
    }
}
