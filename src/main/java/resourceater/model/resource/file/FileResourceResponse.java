package resourceater.model.resource.file;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class FileResourceResponse implements Response {
    private final String resourceId;
    private final long size;
    private final String path;
}
