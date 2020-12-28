package resourceater.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import resourceater.model.resource.CreateRequest;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@RequiredArgsConstructor
public abstract class ResourceController<RQ extends CreateRequest, R extends Resource<R>> {
    private final PagedResourcesAssembler<R> pagedAssembler;
    private final ModelAssembler<R> modelAssembler;
    protected final ResourceRepository<R> repository;

    @GetMapping
    @ApiOperation("Fetches all of the resources")
    public PagedModel<Model<R>> findAll(Pageable pageable) {
        return pagedAssembler.toModel(repository.findAll(pageable), modelAssembler);
    }

    @GetMapping("{id}")
    @ApiOperation("Fetches a resource by its ID")
    public Model<R> findById(@PathVariable String id) {
        return repository.findById(id)
            .map(modelAssembler::toModel)
            .orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Creates a resource")
    public Model<R> create(@RequestBody(required = false) RQ request) {
        return modelAssembler.toModel(repository.save(createResource(request)));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes all of the resources")
    public void deleteAll() {
        repository.deleteAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes a resource by its ID ")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }

    abstract R createResource(RQ request);

    private ResponseStatusException notFound() {
        return new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }
}
