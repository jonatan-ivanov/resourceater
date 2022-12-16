package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.cpu.CpuResourceModel;
import resourceater.model.resource.cpu.CreateCpuResourceRequest;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.CORES;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CORES)
@ExposesResourceFor(CpuResourceModel.class)
@Tag(name = "CPU Cores")
public class CpuResourceController extends ResourceController<CreateCpuResourceRequest, CpuResource> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public CpuResourceController(
        PagedResourcesAssembler<CpuResource> pagedAssembler,
        ModelAssembler<CpuResource> modelAssembler,
        ResourceRepository<CpuResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    CpuResource createResource(CreateCpuResourceRequest request) {
        return new CpuResource(request);
    }
}
