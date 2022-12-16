package resourceater.model.resource.clazz;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class ClassResourceModel extends Model<ClassResource> {
    int size;

    public ClassResourceModel(ClassResource resource, int size) {
        super(resource);
        this.size = size;
    }
}
