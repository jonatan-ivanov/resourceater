package resourceater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.file.FileResource;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.thread.ThreadResource;
import resourceater.repository.ResourceRepository;

/**
 * @author Jonatan Ivanov
 */
@Configuration
class RepositoryConfig {
    @Bean ResourceRepository<HeapResource> heapResourceRepository() {
        return new ResourceRepository<>();
    }

    @Bean ResourceRepository<ThreadResource> threadResourceRepository() {
        return new ResourceRepository<>();
    }

    @Bean ResourceRepository<FileResource> fileResourceRepository() {
        return new ResourceRepository<>();
    }

    @Bean ResourceRepository<CpuResource> cpuResourceRepository() {
        return new ResourceRepository<>();
    }

    @Bean ResourceRepository<ClassResource> classResourceRepository() {
        return new ResourceRepository<>();
    }
}
