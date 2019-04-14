package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.clazz.ClassResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/classPools")
public class ClassResourceController extends ResourceController<ClassResourceRequest, ClassResource> {
    public ClassResourceController(ResourceRepository<ClassResource> repository) {
        super(repository);
    }

    @Override
    ClassResource createResource(ClassResourceRequest request) {
        return new ClassResource(request);
    }
}
