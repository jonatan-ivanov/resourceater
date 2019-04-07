package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.file.FileResource;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources/files")
public class FileResourceController {
    private final ResourceRepository<FileResource> repository;

    @GetMapping
    public Iterable<FileResource> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<FileResource> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    public FileResource create(@RequestBody FileResource resource) {
        return repository.save(resource);
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
