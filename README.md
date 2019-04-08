# resourceater

http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/metrics/jvm.memory.used
http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:heap
http://localhost:8080/actuator/metrics/jvm.memory.used?tag=area:nonheap
http://localhost:8080/actuator/metrics/jvm.threads.states

`docker run -it -p 8080:8080 -p 8686:8686 -v logs:/app/logs jonatanivanov/resourceater:tag`