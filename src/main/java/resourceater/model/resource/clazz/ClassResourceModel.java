package resourceater.model.resource.clazz;

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
public class ClassResourceModel extends Model<ClassResource> {
    private final String id;
    private final int size;
}
