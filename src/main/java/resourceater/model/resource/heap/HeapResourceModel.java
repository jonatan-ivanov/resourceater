package resourceater.model.resource.heap;

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
public class HeapResourceModel extends Model<HeapResource> {
    private final String id;
    private final long size;
}
