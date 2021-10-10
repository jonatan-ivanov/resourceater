package resourceater.actuator.info;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;

import static java.time.Duration.between;

@Slf4j
@RequiredArgsConstructor
public class RuntimeInfoContributor implements InfoContributor {
    private final Environment environment;
    private final Instant startTime = Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("environment",
            Map.of("activeProfiles", environment.getActiveProfiles())
        );

        builder.withDetail("runtime", Map.of(
            "memory", memoryInfo(),
            "cpu", cpuInfo(),
            "java", javaInfo(),
            "gcs", gcInfo(),
            "user", userInfo(),
            "os", osInfo(),
            "network", networkInfo(),
            "startTime", startTime,
            "uptime", between(startTime, Instant.now()),
            "heartbeat", Instant.now())
        );
    }

    private Map<String, Object> memoryInfo() {
        return Map.of(
            "total", Runtime.getRuntime().totalMemory(),
            "max", Runtime.getRuntime().maxMemory(),
            "free", Runtime.getRuntime().freeMemory()
        );
    }

    private Map<String, Object> cpuInfo() {
        return Map.of("availableProcessors", Runtime.getRuntime().availableProcessors());
    }

    private Map<String, Object> javaInfo() {
        return Map.of(
            "class.version", getProperty("java.class.version"),
            "version", getProperty("java.version"),
            "version.date", getProperty("java.version.date"),
            "runtime", Map.of(
                "name", getProperty("java.runtime.name"),
                "version", getProperty("java.runtime.version")
            ),
            "vendor", Map.of(
                "name", getProperty("java.vendor"),
                "version", getProperty("java.vendor.version")
            ),
            "vm", Map.of(
                "name", getProperty("java.vm.name"),
                "vendor", getProperty("java.vm.vendor"),
                "version", getProperty("java.vm.version"),
                "specification", Map.of(
                    "name", getProperty("java.vm.specification.name"),
                    "vendor", getProperty("java.vm.specification.vendor"),
                    "version", getProperty("java.vm.specification.version")
                )
            ),
            "specification", Map.of(
                "name", getProperty("java.specification.name"),
                "vendor", getProperty("java.specification.vendor"),
                "version", getProperty("java.specification.version")
            ),
            "compilation.name", ManagementFactory.getCompilationMXBean().getName(),
            "file.encoding", getProperty("file.encoding")
        );
    }

    private List<Map<String, Object>> gcInfo() {
        return ManagementFactory.getGarbageCollectorMXBeans().stream()
            .map(this::toInfoObject)
            .toList();
    }

    private Map<String, Object> toInfoObject(GarbageCollectorMXBean gcMXBean) {
        return Map.of(
            "name", gcMXBean.getName(),
            "objectName", String.valueOf(gcMXBean.getObjectName()),
            "collectionCount", gcMXBean.getCollectionCount(),
            "collectionTime", gcMXBean.getCollectionTime(),
            "memoryPoolNames", gcMXBean.getMemoryPoolNames()
        );
    }

    private Map<String, Object> userInfo() {
        return Map.of(
            "timezone", getProperty("user.timezone"),
            "country", getProperty("user.country"),
            "language", getProperty("user.language"),
            "dir", getProperty("user.dir")
        );
    }

    private Map<String, Object> osInfo() {
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        return Map.of(
            "arch", osMXBean.getArch(),
            "name", osMXBean.getName(),
            "version", osMXBean.getVersion()
        );
    }

    private Map<String, Object> networkInfo() {
        return Map.of(
            "host", hostName(),
            "ip", ipAddress()
        );
    }


    private String hostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (Exception exception) {
            log.error("Unable to fetch hostname", exception);
            return "n/a";
        }
    }

    private String ipAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception exception) {
            log.error("Unable to fetch IP", exception);
            return "n/a";
        }
    }

    private String getProperty(String key) {
        String value = System.getProperty(key);
        return value != null ? value : "null";
    }
}
