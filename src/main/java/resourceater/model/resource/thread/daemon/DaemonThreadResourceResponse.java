package resourceater.model.resource.thread.daemon;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
class DaemonThreadResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
