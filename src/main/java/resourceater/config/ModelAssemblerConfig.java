package resourceater.config;

import resourceater.controller.ClassResourceController;
import resourceater.controller.ContainerThreadResourceController;
import resourceater.controller.CpuResourceController;
import resourceater.controller.DaemonThreadResourceController;
import resourceater.controller.FileResourceController;
import resourceater.controller.HeapResourceController;
import resourceater.controller.HttpResourceController;
import resourceater.controller.MeterResourceController;
import resourceater.controller.ModelAssembler;
import resourceater.controller.OffHeapResourceController;
import resourceater.controller.SocketResourceController;
import resourceater.model.resource.clazz.ClassResource;
import resourceater.model.resource.clazz.ClassResourceModel;
import resourceater.model.resource.cpu.CpuResource;
import resourceater.model.resource.cpu.CpuResourceModel;
import resourceater.model.resource.file.FileResource;
import resourceater.model.resource.file.FileResourceModel;
import resourceater.model.resource.heap.HeapResource;
import resourceater.model.resource.heap.HeapResourceModel;
import resourceater.model.resource.micrometer.MeterResource;
import resourceater.model.resource.micrometer.MeterResourceModel;
import resourceater.model.resource.network.http.HttpResource;
import resourceater.model.resource.network.http.HttpResourceModel;
import resourceater.model.resource.network.socket.SocketResource;
import resourceater.model.resource.network.socket.SocketResourceModel;
import resourceater.model.resource.offheap.OffHeapResource;
import resourceater.model.resource.offheap.OffHeapResourceModel;
import resourceater.model.resource.thread.container.ContainerThreadResource;
import resourceater.model.resource.thread.container.ContainerThreadResourceModel;
import resourceater.model.resource.thread.daemon.DaemonThreadResource;
import resourceater.model.resource.thread.daemon.DaemonThreadResourceModel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.EntityLinks;

/**
 * @author Jonatan Ivanov
 */
@Configuration
public class ModelAssemblerConfig {
    @Bean
    ModelAssembler<ClassResource> classResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, ClassResourceController.class, ClassResourceModel.class);
    }

    @Bean
    ModelAssembler<ContainerThreadResource> containerThreadResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, ContainerThreadResourceController.class, ContainerThreadResourceModel.class);
    }

    @Bean
    ModelAssembler<CpuResource> cpuResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, CpuResourceController.class, CpuResourceModel.class);
    }

    @Bean
    ModelAssembler<DaemonThreadResource> daemonThreadResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, DaemonThreadResourceController.class, DaemonThreadResourceModel.class);
    }

    @Bean
    ModelAssembler<FileResource> fileResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, FileResourceController.class, FileResourceModel.class);
    }

    @Bean
    ModelAssembler<HeapResource> heapResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, HeapResourceController.class, HeapResourceModel.class);
    }

    @Bean
    ModelAssembler<HttpResource> httpResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, HttpResourceController.class, HttpResourceModel.class);
    }

    @Bean
    ModelAssembler<OffHeapResource> offHeapResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, OffHeapResourceController.class, OffHeapResourceModel.class);
    }

    @Bean
    ModelAssembler<SocketResource> socketResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, SocketResourceController.class, SocketResourceModel.class);
    }

    @Bean
    ModelAssembler<MeterResource> meterResourceModelAssembler(EntityLinks entityLinks) {
        return new ModelAssembler<>(entityLinks, MeterResourceController.class, MeterResourceModel.class);
    }
}
