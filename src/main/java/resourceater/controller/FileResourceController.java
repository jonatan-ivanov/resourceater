package resourceater.controller;

import static resourceater.controller.Mappings.FILES;

import io.swagger.annotations.Api;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.file.CreateFileResourceRequest;
import resourceater.model.resource.file.FileResource;
import resourceater.model.resource.file.FileResourceModel;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(FILES)
@ExposesResourceFor(FileResourceModel.class)
@Api(tags = {"Files"})
public class FileResourceController extends ResourceController<CreateFileResourceRequest, FileResource> {
    public FileResourceController(
        PagedResourcesAssembler<FileResource> pagedAssembler,
        ModelAssembler<FileResource> modelAssembler,
        ResourceRepository<FileResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    FileResource createResource(CreateFileResourceRequest request) {
        return new FileResource(request);
    }
}
