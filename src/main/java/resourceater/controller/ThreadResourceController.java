package resourceater.controller;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resourceater.model.resource.thread.ThreadResource;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jonatan Ivanov
 */
@RestController
public class ThreadResourceController implements DisposableBean {
    private final Map<Integer, ThreadResource> threadResources = new ConcurrentHashMap<>();

    @GetMapping("/resources/threadPools")
    public Collection<ThreadResource> threadPools() {
        return threadResources.values();
    }

    @GetMapping("/resources/threadPools/{id}")
    public ThreadResource threadPool(@PathVariable int id) {
        return threadResources.get(id);
    }

    @PostMapping("/resources/threadPools")
    public ThreadResource createThreadPool(
        @RequestBody ThreadResource threadResource,
        @RequestParam(required = false, defaultValue = "true") boolean permanent) {
        if (permanent) {
            threadResources.put(threadResource.getId(), threadResource);
        }

        return threadResource;
    }

    @DeleteMapping("/resources/threadPools")
    public void deleteThreadPools() {
        threadResources.values().forEach(ThreadResource::shutdown);
        threadResources.clear();
    }

    @DeleteMapping("/resources/threadPools/{id}")
    public void deleteThreadPool(@PathVariable int id) {
        threadResources.remove(id).shutdown();
    }

    @Override
    public void destroy() throws Exception {
        deleteThreadPools();
    }
}
