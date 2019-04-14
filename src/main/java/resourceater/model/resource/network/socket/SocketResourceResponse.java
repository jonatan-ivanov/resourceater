package resourceater.model.resource.network.socket;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import resourceater.model.resource.Response;

import java.util.List;

/**
 * @author Jonatan Ivanov
 */
@Value
@ToString
@Builder
class SocketResourceResponse implements Response {
    private final String resourceId;
    private final int size;
    private final List<Integer> ports;
}
