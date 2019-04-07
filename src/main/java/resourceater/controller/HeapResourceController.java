package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.heap.HeapResource;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequiredArgsConstructor
public class HeapResourceController {
    private final ResourceRepository<HeapResource> repository;

    @GetMapping("/resources/objects")
    public Iterable<HeapResource> findAll() {
        return repository.findAll();
    }

    @GetMapping("/resources/objects/{id}")
    public Optional<HeapResource> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping("/resources/objects")
    public HeapResource create(@RequestBody HeapResource resource, @RequestParam(required = false, defaultValue = "true") boolean permanent) {
        return permanent ? repository.save(resource) : resource;
    }

    @DeleteMapping("/resources/objects")
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping("/resources/objects/{id}")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}
