package resourceater.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.client.HttpBlobClient;
import resourceater.model.resource.network.http.HttpResource;
import resourceater.repository.ResourceRepository;

import static resourceater.controller.Mappings.HTTP_CLIENTS;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequestMapping(HTTP_CLIENTS)
public class HttpResourceController extends ResourceController<Void, HttpResource> {
    private final String url;
    private final HttpBlobClient client;

    public HttpResourceController(
        ResourceRepository<HttpResource> repository,
        HttpBlobClient client,
        @Value("${feign.client.config.httpBlob.url}") String url) {
        super(repository);
        this.client = client;
        this.url = url;
    }

    @Override
    HttpResource createResource(Void request) {
        return new HttpResource(client, url);
    }
}

