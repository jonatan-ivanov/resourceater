package resourceater.model.resource.thread.daemon;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
@EqualsAndHashCode(callSuper=true)
class DaemonThreadResourceResponse extends Response {
    private final String resourceId;
    private final int size;
}
