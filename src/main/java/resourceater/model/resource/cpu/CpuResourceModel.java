package resourceater.model.resource.cpu;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class CpuResourceModel extends Model<CpuResource> {
    int size;

    public CpuResourceModel(CpuResource resource, int size) {
        super(resource);
        this.size = size;
    }
}
