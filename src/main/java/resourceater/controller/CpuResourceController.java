package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.cpu.CpuResourceRequest;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.CORES;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/" + CORES)
public class CpuResourceController extends ResourceController<CpuResourceRequest, CpuResource> {
    public CpuResourceController(ResourceRepository<CpuResource> repository) {
        super(repository);
    }

    @Override
    CpuResource createResource(CpuResourceRequest request) {
        return new CpuResource(request);
    }

    @Override
    String getRel() {
        return CORES;
    }
}
