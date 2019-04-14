package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
@RequiredArgsConstructor
public abstract class ResourceController<RQ, R extends Resource> {
    protected final ResourceRepository<R> repository;

    @GetMapping
    public Iterable<Response> findAll() {
        return toStream(repository.findAll())
            .map(R::toResponse)
            .collect(toList());
    }

    @GetMapping("{id}")
    public Optional<Response> findById(@PathVariable String id) {
        return repository.findById(id).map(R::toResponse);
    }

    @PostMapping
    public Response create(@RequestBody(required = false) RQ request) {
        return repository.save(createResource(request)).init().toResponse();
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }

    abstract R createResource(RQ request);
}
