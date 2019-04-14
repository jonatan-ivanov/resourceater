package resourceater.model.resource.clazz;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class ClassResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
