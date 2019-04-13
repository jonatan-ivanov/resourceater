package resourceater.model.resource.heap;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
class HeapResourceResponse implements Response {
    private final String resourceId;
    private final long size;
}
