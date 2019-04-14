package resourceater.model.resource.thread.container;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class ContainerThreadResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
