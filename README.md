# resourceater

Resourceater is a Java based web service which consumes your resources on demand. It can be useful to simulate high resource needs. It can consume the following resources:

- Heap by creating byte arrays (this also simulates memory leaks)
- Off-Heap by allocating direct byte buffers
- CPU by spinning up threads which can make the cores busy
- Disk by creating big files
- Threads by creating simple daemon threads which are doing nothing
- Servlet Container Threads by calling itself and blocking the Container's (Tomcat) thread
- Classes by generating new classes and loading them on-the-fly
- Network by downloading large files over HTTP
- Sockets by creating server sockets

# Endpoints
Check [ReDoc](https://jonatan-ivanov.github.io/resourceater/) or the [Swagger-UI](http://localhost:8080/swagger-ui/)

# Examples

## Heap

```sh
curl -s -X POST "http://localhost:8080/resources/objects" -H "Content-Type: application/json" -d "{ \"size\": \"1MB\"}"
```

## Off-Heap

```sh
curl -s -X POST "http://localhost:8080/resources/directBuffers" -H "Content-Type: application/json" -d "{ \"size\": \"1MB\"}"
```

## CPU

```sh
curl -s -X POST "http://localhost:8080/resources/cores" -H "Content-Type: application/json" -d "{ \"size\": 1}"
```

## Disk

```sh
curl -s -X POST "http://localhost:8080/resources/files" -H "Content-Type: application/json" -d "{ \"size\": \"1MB\"}"
```

## Threads

```sh
curl -s -X POST "http://localhost:8080/resources/threadPools" -H "Content-Type: application/json" -d "{ \"size\": 1}"
```

## Container Threads

```sh
curl -s -X POST "http://localhost:8080/resources/containerThreads" -H "Content-Type: application/json" -d "{ \"size\": 1}"
```

## Classes

```sh
curl -s -X POST "http://localhost:8080/resources/classPools" -H "Content-Type: application/json" -d "{ \"size\": 1}"
```

## Network

```sh
curl -s -X POST "http://localhost:8080/resources/httpClients"
```

## Sockets

```sh
curl -s -X POST "http://localhost:8080/resources/socketPools" -H "Content-Type: application/json" -d "{ \"size\": 1}"
```

# Monitoring

The project is using [micrometer](https://micrometer.io/) and it also opens JMX on port `8686`; they give you a lot of information about what is going on under the hood, e.g.:

- Metrics endpoint: [/actuator/metrics](http://localhost:8080/actuator/metrics)
- Heap usage: [/actuator/metrics/jvm.memory.used?tag=area:heap](http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap)
- Direct byte buffers (Off Heap): [/actuator/metrics/jvm.buffer.memory.used?tag=id:direct](http://localhost:8080/actuator/metrics/jvm.buffer.memory.used?tag=id:direct)
- CPU usage: [/actuator/metrics/process.cpu.usage](http://localhost:8080/actuator/metrics/process.cpu.usage)
- Number of CPU cores: [/actuator/metrics/system.cpu.count](http://localhost:8080/actuator/metrics/system.cpu.count)


- Daemon thread count: [/actuator/metrics/jvm.threads.daemon](http://localhost:8080/actuator/metrics/jvm.threads.daemon)
- Loaded Classes: [/actuator/metrics/jvm.classes.loaded](http://localhost:8080/actuator/metrics/jvm.classes.loaded)
- Container (Tomcat) Threads: [/actuator/metrics/tomcat.threads.current](http://localhost:8080/actuator/metrics/tomcat.threads.current)

# Docker

`docker run -it -p 8080:8080 -p 8686:8686 -v logs:/app/logs jonatanivanov/resourceater:tag`
