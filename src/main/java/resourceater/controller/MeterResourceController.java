package resourceater.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.micrometer.CreateMeterResourceRequest;
import resourceater.model.resource.micrometer.MeterResource;
import resourceater.model.resource.micrometer.MeterResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static resourceater.controller.Mappings.METERS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(METERS)
@ExposesResourceFor(MeterResourceModel.class)
@Tag(name = "Micrometer Meter Objects")
public class MeterResourceController extends ResourceController<CreateMeterResourceRequest, MeterResource> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MeterResourceController(
        PagedResourcesAssembler<MeterResource> pagedAssembler,
        ModelAssembler<MeterResource> modelAssembler,
        ResourceRepository<MeterResource> repository) {
        super(pagedAssembler, modelAssembler, repository);
    }

    @Override
    MeterResource createResource(CreateMeterResourceRequest request) {
        return new MeterResource(request);
    }
}
