package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;
import resourceater.repository.ResourceRepository;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
@RequiredArgsConstructor
public abstract class ResourceController<RQ, R extends Resource> {
    protected final ResourceRepository<R> repository;

    @GetMapping
    public Iterable<Response> findAll() {
        Iterable<Response> responses = toStream(repository.findAll())
            .map(R::toResponse)
            .map(this::enhance)
            .collect(toList());

        return enhance(responses);
    }

    @GetMapping("{id}")
    public Response findById(@PathVariable String id) {
        return repository.findById(id)
            .map(R::toResponse)
            .map(this::enhance)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Unable to find resource: %s", id)));
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody(required = false) RQ request) throws URISyntaxException {
        Response response = enhance(repository.save(createResource(request)).init().toResponse());
        return ResponseEntity
            .created(new URI(response.getId().expand().getHref()))
            .body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    abstract R createResource(RQ request);

    private Iterable<Response> enhance(Iterable<Response> responses) {
        return new Resources<>(responses, linkTo(methodOn(this.getClass()).findAll()).withSelfRel());
    }

    private Response enhance(Response response) {
        response.add(
            linkTo(methodOn(this.getClass()).findById(response.getResourceId())).withSelfRel(),
            linkTo(methodOn(this.getClass()).findAll()).withRel("collection")
        );

        return response;
    }
}
