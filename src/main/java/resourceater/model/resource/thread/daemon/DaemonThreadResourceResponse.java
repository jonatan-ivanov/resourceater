package resourceater.model.resource.thread.daemon;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class DaemonThreadResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
