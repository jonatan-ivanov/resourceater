package resourceater.model.resource.cpu;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class CpuResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
