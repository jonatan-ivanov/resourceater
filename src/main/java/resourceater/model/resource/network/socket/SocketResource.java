package resourceater.model.resource.network.socket;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class SocketResource implements Resource<SocketResource> {
    private final List<ServerSocket> sockets = new ArrayList<>();

    public SocketResource(CreateSocketResourceRequest request) {
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
    public Model<SocketResource> toModel() {
        return SocketResourceModel.builder()
            .id(getId())
            .size(sockets.size())
            .ports(sockets.stream().map(ServerSocket::getLocalPort).collect(toList()))
            .build();
    }
}
