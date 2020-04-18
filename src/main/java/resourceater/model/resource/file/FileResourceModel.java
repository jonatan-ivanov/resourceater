package resourceater.model.resource.file;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
@EqualsAndHashCode(callSuper=true)
public class FileResourceModel extends Model<FileResource> {
    private final String id;
    private final long size;
    private final String path;
}
