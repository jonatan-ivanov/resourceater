package resourceater.model.resource.file;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
public class FileResourceResponse implements Response {
    private final String resourceId;
    private final long size;
    private final String path;
}
