package resourceater.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.clazz.ClassResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.CLASS_POOLS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CLASS_POOLS)
@Api(tags = {"Classes"})
public class ClassResourceController extends ResourceController<ClassResourceRequest, ClassResource> {
    public ClassResourceController(ResourceRepository<ClassResource> repository) {
        super(repository);
    }

    @Override
    ClassResource createResource(ClassResourceRequest request) {
        return new ClassResource(request);
    }
}
