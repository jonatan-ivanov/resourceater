run:
	./gradlew assemble
	java --version
	java \
		-server \
		-XX:NativeMemoryTracking=summary \
		-XX:MinHeapSize=1G \
		-XX:MaxHeapSize=1G \
		-XX:MaxDirectMemorySize=1G \
		-XX:MaxMetaspaceSize=256M \
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
		-jar build/libs/resourceater-*.jar

perftest:
	./gradlew gatlingRun --rerun-tasks
