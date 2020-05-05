package resourceater.controller;

import static org.springframework.hateoas.IanaLinkRelations.COLLECTION;
import static org.springframework.hateoas.IanaLinkRelations.SELF;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class ModelAssembler<R extends Resource<R>> extends RepresentationModelAssemblerSupport<R, Model<R>> {
    private final EntityLinks links;

    public ModelAssembler(EntityLinks links, Class<?> controllerClass, Class<? extends Model<R>> modelClass) {
        super(controllerClass, (Class<Model<R>>) modelClass);
        this.links = links;
    }

    @Override
    protected Model<R> instantiateModel(R resource) {
        return resource.toModel();
    }

    @Override
    public Model<R> toModel(R resource) {
        return createModelWithId(resource.getId(), resource)
            .add(links.linkToCollectionResource(getResourceType()).withRel(COLLECTION));
    }

    @Override
    public CollectionModel<Model<R>> toCollectionModel(Iterable<? extends R> entities) {
        return new CollectionModel<>(
            super.toCollectionModel(entities).getContent(),
            links.linkToCollectionResource(getResourceType()).withRel(SELF)
        );
    }
}
