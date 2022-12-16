package resourceater.model.resource.heap;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class HeapResourceModel extends Model<HeapResource> {
    long size;

    public HeapResourceModel(HeapResource resource, long size) {
        super(resource);
        this.size = size;
    }
}
