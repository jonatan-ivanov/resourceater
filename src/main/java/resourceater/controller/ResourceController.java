package resourceater.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.heap.HeapResource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonatan Ivanov
 */
@RestController
public class ResourceController {
    private final Map<Integer, HeapResource> heapResources = new HashMap<>();

    @GetMapping("/resources/objects")
    public Collection<HeapResource> objects() {
        return heapResources.values();
    }

    @GetMapping("/resources/objects/{id}")
    public HeapResource object(@PathVariable int id) {
        return heapResources.get(id);
    }

    @PostMapping("/resources/objects")
    public HeapResource createObject(
        @RequestBody HeapResource heapResource,
        @RequestParam(required = false, defaultValue = "true") boolean permanent) {
        if (permanent) {
            heapResources.put(heapResource.getId(), heapResource);
        }

        return heapResource;
    }

    @DeleteMapping("/resources/objects")
    public void deleteObjects() {
        heapResources.clear();
    }

    @DeleteMapping("/resources/objects/{id}")
    public void deleteObject(@PathVariable int id) {
        heapResources.remove(id);
    }
}
