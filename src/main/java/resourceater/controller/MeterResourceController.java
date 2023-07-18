package resourceater.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import resourceater.model.resource.micrometer.CreateMeterResourceRequest;
import resourceater.model.resource.micrometer.MeterResource;
import resourceater.model.resource.micrometer.MeterResourceModel;
import resourceater.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Value;
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

    private final MeterRegistry registry;
    private final boolean useBootRegistry;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MeterResourceController(
        PagedResourcesAssembler<MeterResource> pagedAssembler,
        ModelAssembler<MeterResource> modelAssembler,
        ResourceRepository<MeterResource> repository,
        MeterRegistry registry,
        @Value("${resourceater.meter-resource.use-boot-registry}") boolean useBootRegistry) {
        super(pagedAssembler, modelAssembler, repository);
        this.registry = registry;
        this.useBootRegistry = useBootRegistry;
    }

    @Override
    MeterResource createResource(CreateMeterResourceRequest request) {
        return new MeterResource(request, (useBootRegistry) ? registry : new SimpleMeterRegistry());
    }
}
