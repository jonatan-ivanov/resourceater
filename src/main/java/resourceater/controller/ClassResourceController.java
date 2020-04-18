package resourceater.controller;

import static resourceater.controller.Mappings.CLASS_POOLS;

import io.swagger.annotations.Api;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.clazz.ClassResourceModel;
import resourceater.model.resource.clazz.CreateClassResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CLASS_POOLS)
@ExposesResourceFor(ClassResourceModel.class)
@Api(tags = {"Classes"})
public class ClassResourceController extends ResourceController<CreateClassResourceRequest, ClassResource> {
    public ClassResourceController(ModelAssembler<ClassResource> modelAssembler, ResourceRepository<ClassResource> repository) {
        super(modelAssembler, repository);
    }

    @Override
    ClassResource createResource(CreateClassResourceRequest request) {
        return new ClassResource(request);
    }
}
