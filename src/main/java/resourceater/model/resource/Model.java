package resourceater.model.resource;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author Jonatan Ivanov
 */
public abstract class Model<R extends Resource<R>> extends RepresentationModel<Model<R>> {
    public abstract String getId();
}
