package resourceater.model.resource.micrometer;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class MeterResourceModel extends Model<MeterResource> {
    int size;
//    List<String> meters;

    public MeterResourceModel(MeterResource resource) {
        super(resource);
//        this.meters = resource.getMetersAsString().lines().toList();
        this.size = resource.getMeters().size();
    }
}
