package resourceater.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.cpu.CpuResourceRequest;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping("/resources/cores")
public class CpuResourceController extends ResourceController<CpuResourceRequest, CpuResource> {
    public CpuResourceController(ResourceRepository<CpuResource> repository) {
        super(repository);
    }

    @Override
    CpuResource createResource(CpuResourceRequest request) {
        return new CpuResource(request);
    }
}
