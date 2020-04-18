package resourceater.controller;

import static resourceater.controller.Mappings.CORES;

import io.swagger.annotations.Api;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.cpu.CpuResourceModel;
import resourceater.model.resource.cpu.CreateCpuResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(CORES)
@ExposesResourceFor(CpuResourceModel.class)
@Api(tags = {"CPU Cores"})
public class CpuResourceController extends ResourceController<CreateCpuResourceRequest, CpuResource> {
    public CpuResourceController(ModelAssembler<CpuResource> modelAssembler, ResourceRepository<CpuResource> repository) {
        super(modelAssembler, repository);
    }

    @Override
    CpuResource createResource(CreateCpuResourceRequest request) {
        return new CpuResource(request);
    }
}
