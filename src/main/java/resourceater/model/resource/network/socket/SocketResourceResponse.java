package resourceater.model.resource.network.socket;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Response;

import java.util.List;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
@EqualsAndHashCode(callSuper=true)
class SocketResourceResponse extends Response {
    private final String resourceId;
    private final int size;
    private final List<Integer> ports;
}
