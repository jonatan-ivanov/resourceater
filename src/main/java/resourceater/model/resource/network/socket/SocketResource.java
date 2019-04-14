package resourceater.model.resource.network.socket;

import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class SocketResource implements Resource {
    private final List<ServerSocket> sockets = new ArrayList<>();

    public SocketResource(SocketResourceRequest request) {
        this(request.getSize());
    }

    private SocketResource(int count) {
        for (int i = 0; i < count; i++) {
            allocateSocket().ifPresent(sockets::add);
        }

        if (sockets.isEmpty()) {
            throw new IllegalStateException("No sockets were created");
        }
    }

    private Optional<ServerSocket> allocateSocket() {
        try {
            return Optional.of(new ServerSocket(0));
        }
        catch (IOException e) {
            log.warn("Unable to create socket", e);
            return Optional.empty();
        }
    }

    @Override
    public Response toResponse() {
        return SocketResourceResponse.builder()
            .resourceId(getId())
            .size(sockets.size())
            .ports(sockets.stream().map(ServerSocket::getLocalPort).collect(toList()))
            .build();
    }
}
