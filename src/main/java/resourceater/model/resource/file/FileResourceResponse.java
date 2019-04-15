package resourceater.model.resource.file;

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
class FileResourceResponse extends Response {
    private final String resourceId;
    private final long size;
    private final String path;
}
