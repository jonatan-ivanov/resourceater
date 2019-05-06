package resourceater.controller;

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
public class FileResourceController extends ResourceController<FileResourceRequest, FileResource> {
    public FileResourceController(ResourceRepository<FileResource> repository) {
        super(repository);
    }

    @Override
    FileResource createResource(FileResourceRequest request) {
        return new FileResource(request);
    }
}
