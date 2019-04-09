package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.Response;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.heap.HeapResourceRequest;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources/objects")
public class HeapResourceController {
    private final ResourceRepository<HeapResource> repository;

    @GetMapping
    public Iterable<Response> findAll() {
        return toStream(repository.findAll())
            .map(HeapResource::toResponse)
            .collect(toList());
    }

    @GetMapping("{id}")
    public Optional<Response> findById(@PathVariable String id) {
        return repository.findById(id).map(HeapResource::toResponse);
    }

    @PostMapping
    public Response create(
        @RequestBody HeapResourceRequest request,
        @RequestParam(required = false, defaultValue = "true") boolean permanent) {
        HeapResource resource = new HeapResource(request);
        return (permanent ? repository.save(resource) : resource).toResponse();
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
