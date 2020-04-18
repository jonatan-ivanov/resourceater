package resourceater.model.resource.offheap;

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
public class OffHeapResourceModel extends Model<OffHeapResource> {
    private final String id;
    private final long size;
}
