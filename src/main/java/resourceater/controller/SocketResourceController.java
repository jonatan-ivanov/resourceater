package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.network.socket.SocketResource;
import resourceater.model.resource.network.socket.SocketResourceRequest;
import resourceater.repository.ResourceRepository;

@RestController
@RequestMapping("/resources/socketPools")
public class SocketResourceController extends ResourceController<SocketResourceRequest, SocketResource> {
    public SocketResourceController(ResourceRepository<SocketResource> repository) {
        super(repository);
    }

    @Override
    SocketResource createResource(SocketResourceRequest request) {
        return new SocketResource(request);
    }
}
