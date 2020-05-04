package resourceater.model.resource.file;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class FileResourceModel extends Model<FileResource> {
    private final long size;
    private final String path;

    public FileResourceModel(FileResource resource, long size, String path) {
        super(resource);
        this.size = size;
        this.path = path;
    }
}
