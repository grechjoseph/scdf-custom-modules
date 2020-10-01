<h1>Spring Cloud Data Flow Custom Processor</h1>

<h2>Contents</h2>
<ol>
    <li><a href="#h2_summary">Summary</a></li>
    <li><a href="#h2_profiles">Profiles</a></li>
    <li><a href="#h2_dependencies">Dependencies</a></li>
    <li><a href="#h2_plugins">Plugins</a></li>
    <li><a href="#h2_classes">Classes</a></li>
    <li><a href="#h2_properties">Properties</a></li>
    <li>
        <a href="#h2_registering_to_spring_cloud_data_flow">Registering to Spring Cloud Data Flow</a>
        <ol>
            <li><a href="#h3_prerequisites">Prerequisites</a></li>
            <li><a href="#h3_registering">Registering</a></li>
        </ol>
    </li>
    <li><a href="#h2_creating_a_stream">Creating a Stream</a></li>
    <li><a href="#h2_notes">Notes</a></li>
</ol>

<h2 id="h2_summary">Summary:</h2>
These applications are an example of creating an app to be registered on Spring Cloud Data Flow. 

The three applications are:
<ol>
    <li>scdf-custom-source: Publishes a MyMessage object with an ID and value to the stream, and listens on a custom RabbitMQ queue for acknowledgement from the scdf-custom-sink application.</li>
    <li>scdf-custom-processor: Listens to stream message from scdf-custom-source, appends text to the existing message's value, and publishes back to the stream.</li>
    <li>scdf-custom-sink: Listens to stream message from scdf-custom-processor, and send an acknowledgement message through a custom RabbitMQ queue, expected to be handled by scdf-custom-source.</li>
</ol>

<h2 id="h2_profiles">Profiles</h2>
<ul>
    <li><b>dev</b>: Use this profile in order to run all three applications using a local RabbitMq instance (overridable property spring.rabbitmq.addresses).</li>
</ul>

<h2 id="h2_dependencies">Dependencies:</h2>
<ul>
    <li><b>spring-cloud-stream:</b> To enable cloud stream.</li>
    <li><b>spring-boot-starter-web:</b> To have actuator accessible.</li>
    <li><b>spring-boot-starter-actuator:</b> For Skipper Server to know when the application is up.</li>
    <li><b>spring-cloud-stream-binder-rabbit:</b> To enable rabbitmq binding.</li>
    <li><b>spring-boot-configuration-processor:</b> To generate application properties' json. (See target/classes/META-INF on mvn clean install)</li>
    <li><b>lombok:</b> </li>
</ul>

<h2 id="h2_plugins">Plugins</h2>
<ul>
    <li><b>spring-cloud-app-starter-metadata-maven-plugin:</b> To generate metadata.jar on <b>compile</b> (ie: mvn clean install). Requires spring-configuration-metadata-whitelist.properties / dataflow-configuration-metadata-whitelist.properties, together with dependency <b>spring-boot-configuration-processor</b>  to include in generated metadata.jar.</li>
</ul>

<h2 id="h2_classes">Classes</h2>
<ul>
    <li><b>com.jg.scdfcustom{module}.dto.MyMessage:</b> message DTO to send/receive.</li>
    <li><b>com.jg.scdfcustom{module}.service.MessagingService:</b> has a transformer that received a MyMessage payload message, append its value, and returns it.</li>
    <li><b>com.jg.scdfcustom{module}.config.MyProperties:</b> Contains property field/s.</li>
</ul>

<h2 id="h2_properties">Properties</h2>
<ul>
    <li><b>resources/application.yml: </b>contains properties for the default profile.</li>
    <li><b>resources/META-INF/dataflow-configuration-metadata-whitelist.properties:</b> Required by plugin to include in metadata.jar (to be evaluated by Spring Data Flow 2.X or higher).</li>
    <li><b>resources/META-INF/spring-configuration-metadata-whitelist.properties:</b> Required by plugin to include in metadata.jar (to be evaluated by Spring Data Flow version lower than 2.X).</li>
</ul>

<h2 id="h2_registering_to_spring_cloud_data_flow">Registering to Spring Cloud Data Flow</h2>
<h3 id="h3_prerequisites">Prerequisites</h3>
<ul>
    <li>Navigate to /rabbitmq and run: <b>docker-compose -f docker-compose.yml -f docker-compose-rabbitmq.yml up -d.</b></li>
    <li><b>NOTE: </b> The docker-compose.yml file has been modified to redirect local maven repository to <b>dataflow-server</b> and <b>skipper-server</b> images with lines <b>- /c/Users/jgrech/.m2:/root/.m2</b></li>
    <li><b>NOTE: </b> Any deployed images at runtime will be deployed on the <b>skipper-server</b> image. Therefore, any http server images need to have their port exposed on the <b>skipper-server</b> image beforehand.</li>
    <li><b>NOTE: </b> The docker-compose.yml file has been modified to hardcode version for <b>dataflow-server</b> and <b>skipper-server</b> at line: <b>image: springcloud/spring-cloud-dataflow-server:2.6.1 #${DATAFLOW_VERSION:?DATAFLOW_VERSION is not set!}</b> and line: <b>image: springcloud/spring-cloud-skipper-server:2.5.1 #${SKIPPER_VERSION:?SKIPPER_VERSION is not set!}</b></li>
</ul>

<h3 id="h3_registering">Registering</h3>
<ul>
    <li>Run command: <b>mvn clean install</b></li>
    <li>Verify file created: /target/classes/META-INF/<b>dataflow-configuration-metadata-whitelist.properties</b>.</li>
    <li>Verify file created: /target/classes/META-INF/<b>spring-configuration-metadata.json</b>.</li>
    <li>Verify file created: /target/classes/META-INF/<b>spring-configuration-metadata-whitelist.properties</b>.</li>
    <li>Verify file created: /target/<b>scdf-custom-processor-{version}.jar</b>.</li>
    <li>Verify file created: /target/<b>scdf-custom-processor-{version}-metadata.jar</b>.</li>
    <li>Browse to <a href="http://localhost:9393/dashboard">http://localhost:9393/dashboard</a>.</li>
    <li>Navigate to <b>Apps -> Add Application -> Register one or more application</b>.</li>
    <li>Provide a name, with type 'Processor'.</li>
    <li>URI: <b>maven://com.jg:scdf-custom-processor:1.0.1</b>.</li>
    <li>Metadata URI: <b>maven://com.jg:scdf-custom-processor:jar:metadata:1.0.1</b>.</li>
    <li>Register Application.</li>
    <li>Open the registered application from the list of applications.</li>
    <li>(Source only) Verify property listed: <b>initial-value</b>.</li>
    <li>(Processor only) Verify property listed: <b>append-value</b>.</li>
    <li>Verify property listed: <b>addresses</b>.</li>
    <li>Verify property listed: <b>port</b>.</li>
</ul>

<h2 id="h2_creating_a_stream">Creating a Stream</h2>
<ul>
    <li>Browse to <a href="http://localhost:9393/dashboard">http://localhost:9393/dashboard</a>.</li>
    <li>Navigate to Streams -> Create Stream.</li>
    <li>Add Stream text: <b>{name provided at Registration of Source app} | {name provided at Registration of Processor app} | {name provided at Registration of Sink app}</b>.</li>
    <li>Click <b>Create Stream(s) and name the stream.</b></li>
    <li>Next to the created stream in the list, click the Deploy (play) button.</li>
    <li>Open the Application Properties for the custom source application (should be showing 0/3 before configuring).</li>
    <li>initial-value = Hello</li>
    <li>addresses = rabbitmq:5672 (rabbitmq docker image name in docker-compose-rabbitmq.yml)</li>
    <li>port = 1111.</li>
    <li>Open the Application Properties for the custom processor application (should be showing 0/3 before configuring).</li>
    <li>append-value = World!</li>
    <li>addresses = rabbitmq:5672 (rabbitmq docker image name in docker-compose-rabbitmq.yml)</li>
    <li>port = 2222.</li>
    <li>Open the Application Properties for the custom sink application (should be showing 0/2 before configuring).</li>
    <li>addresses = rabbitmq:5672 (rabbitmq docker image name in docker-compose-rabbitmq.yml)</li>
    <li>port = 3333.</li>
    <li>Once done, click <b>Deploy Stream</b>.</li>
    <li>Open/click the stream from the list of streams.</li>
    <li>If the logs have not been enabled yet, wait until the Stream has status <b>Deployed.</b></li>
    <li>Once logs are accessible, verify that the Scdf-Custom-Source application is logging sending the messages, Scdf-Custom-Processor is logging transforming the message, and that the Scdf-Custom-Sink application is logging the received messages (should include value <b>Hello World!</b> as per config). Lastly, verify that the Sink is publishing the acknowledgement message, and the Source is receiving it.</li>
</ul>

<h2 id="h2_notes">Notes</h2>
<ul>
    <li>To generate new properties, bump version.</li>
    <li>Known issue: Sometimes, when rebuilding the artifact, scdf no longer shows app's the properties. Workaround is currently to bump version of artifact.</li>
</ul>