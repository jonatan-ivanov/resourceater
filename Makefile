run:
	./gradlew clean assemble
	java --version
	java \
		-server \
		-XX:NativeMemoryTracking=summary \
		-XX:MinHeapSize=500M \
		-XX:MaxHeapSize=500M \
		-XX:MaxDirectMemorySize=500M \
		-XX:MaxMetaspaceSize=500M \
		-XX:MaxRAM=750M \
		-XX:+UseG1GC \
		-XX:+HeapDumpOnOutOfMemoryError \
		-XX:HeapDumpPath=logs \
		-Xlog:gc*:file=logs/gc.log:time,uptime,pid,tid,level,tags:filecount=5,filesize=5M \
		-Djava.security.egd=file:/dev/./urandom \
		-Duser.timezone=UTC \
		-Dfile.encoding=UTF-8 \
		-Dcom.sun.management.jmxremote \
		-Dcom.sun.management.jmxremote.port=8686 \
		-Dcom.sun.management.jmxremote.rmi.port=8686 \
		-Dcom.sun.management.jmxremote.local.only=false \
		-Dcom.sun.management.jmxremote.authenticate=false \
		-Dcom.sun.management.jmxremote.ssl=false \
		-Dcom.sun.management.jmxremote.host=127.0.0.1 \
		-Djava.rmi.server.hostname=127.0.0.1 \
		-jar build/libs/resourceater-*.jar &

	sleep 10

	@echo 'Reserving 450MB heap and 450MB off-heap memory...'
	curl -X POST -H 'Content-Type: application/json' --data '{ "size": "450MB" }' http://localhost:8080/resources/objects
	curl -X POST -H 'Content-Type: application/json' --data '{ "size": "450MB" }' http://localhost:8080/resources/directBuffers

	@echo 'And the total memory consumption of the JVM is'
	/usr/bin/top -l 1 | grep `jps | grep -i resourceater | awk '{print $$1}'` | awk '{print $$8}'
	kill `jps | grep -i resourceater | awk '{print $$1}'`

perftest:
	./gradlew clean gatlingRun
