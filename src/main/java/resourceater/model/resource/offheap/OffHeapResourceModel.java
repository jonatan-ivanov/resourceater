package resourceater.model.resource.offheap;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class OffHeapResourceModel extends Model<OffHeapResource> {
    private final long size;

    public OffHeapResourceModel(OffHeapResource resource, long size) {
        super(resource);
        this.size = size;
    }
}
