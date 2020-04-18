package resourceater.model.resource.cpu;

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
public class CpuResourceModel extends Model<CpuResource> {
    private final String id;
    private final int size;
}
