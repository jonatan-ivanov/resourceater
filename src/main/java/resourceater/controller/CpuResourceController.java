package resourceater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.repository.ResourceRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources/cores")
public class CpuResourceController {
    private final ResourceRepository<CpuResource> repository;

    @GetMapping
    public Iterable<CpuResource> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<CpuResource> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    public CpuResource create(@RequestBody CpuResource resource) {
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
