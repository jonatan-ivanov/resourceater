package resourceater.model.resource.file;

import lombok.Value;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateFileResourceRequest {
    private final String size;
}