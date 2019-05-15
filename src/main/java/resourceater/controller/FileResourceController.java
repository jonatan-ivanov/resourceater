package resourceater.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.file.FileResource;
import resourceater.model.resource.file.FileResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.FILES;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(FILES)
@Api(tags = {"Files"})
public class FileResourceController extends ResourceController<FileResourceRequest, FileResource> {
    public FileResourceController(ResourceRepository<FileResource> repository) {
        super(repository);
    }

    @Override
    FileResource createResource(FileResourceRequest request) {
        return new FileResource(request);
    }
}
