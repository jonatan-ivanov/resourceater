package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.client.HttpBlobClient;
import resourceater.model.resource.Response;
import resourceater.model.resource.network.http.HttpResource;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources/httpClients")
public class HttpResourceController {
    @Value("${feign.client.config.httpBlob.url}") private String url;
    private final HttpBlobClient client;
    private final ResourceRepository<HttpResource> repository;

    @GetMapping
    public Iterable<Response> findAll() {
        return toStream(repository.findAll())
            .map(HttpResource::toResponse)
            .collect(toList());
    }

    @GetMapping("{id}")
    public Optional<Response> findById(@PathVariable String id) {
        return repository.findById(id).map(HttpResource::toResponse);
    }

    @PostMapping
    public Response create() {
        return repository.save(new HttpResource(client, url)).toResponse();
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}

