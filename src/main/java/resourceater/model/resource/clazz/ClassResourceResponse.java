package resourceater.model.resource.clazz;

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
class ClassResourceResponse extends Response {
    private final String resourceId;
    private final int size;
}
