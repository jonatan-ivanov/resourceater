package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.clazz.ClassResourceModel;
import resourceater.model.resource.clazz.CreateClassResourceRequest;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.CLASS_POOLS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CLASS_POOLS)
@ExposesResourceFor(ClassResourceModel.class)
@Tag(name = "Classes")
public class ClassResourceController extends ResourceController<CreateClassResourceRequest, ClassResource> {
    public ClassResourceController(
        PagedResourcesAssembler<ClassResource> pagedAssembler,
        ModelAssembler<ClassResource> modelAssembler,
        ResourceRepository<ClassResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    ClassResource createResource(CreateClassResourceRequest request) {
        return new ClassResource(request);
    }
}
