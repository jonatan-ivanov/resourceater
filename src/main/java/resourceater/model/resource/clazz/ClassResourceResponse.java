package resourceater.model.resource.clazz;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
public class ClassResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
