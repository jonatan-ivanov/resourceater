package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.ThreadResource;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

/**
 * @author Jonatan Ivanov
 */
@RestController
@RequiredArgsConstructor
public class ThreadResourceController {
    private final ResourceRepository<ThreadResource> repository;

    @GetMapping("/resources/threadPools")
    public Iterable<ThreadResource> findAll() {
        return repository.findAll();
    }

    @GetMapping("/resources/threadPools/{id}")
    public Optional<ThreadResource> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping("/resources/threadPools")
    public ThreadResource create(@RequestBody ThreadResource resource) {
        return repository.save(resource);
    }

    @DeleteMapping("/resources/threadPools")
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping("/resources/threadPools/{id}")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}
