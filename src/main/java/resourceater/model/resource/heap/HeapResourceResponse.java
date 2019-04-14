package resourceater.model.resource.heap;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class HeapResourceResponse implements Response {
    private final String resourceId;
    private final long size;
}
