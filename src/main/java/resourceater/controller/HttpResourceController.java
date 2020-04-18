package resourceater.controller;

import static resourceater.controller.Mappings.HTTP_CLIENTS;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.client.HttpBlobClient;
import resourceater.model.resource.network.http.HttpResource;
import resourceater.model.resource.network.http.HttpResourceModel;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(HTTP_CLIENTS)
@ExposesResourceFor(HttpResourceModel.class)
@Api(tags = {"Network Traffic over HTTP"})
public class HttpResourceController extends ResourceController<Void, HttpResource> {
    private final String url;
    private final HttpBlobClient client;

    public HttpResourceController(
        ModelAssembler<HttpResource> modelAssembler,
        ResourceRepository<HttpResource> repository,
        HttpBlobClient client,
        @Value("${feign.client.config.httpBlob.url}") String url) {
        super(modelAssembler, repository);
        this.client = client;
        this.url = url;
    }

    @Override
    HttpResource createResource(Void request) {
        return new HttpResource(client, url);
    }
}

