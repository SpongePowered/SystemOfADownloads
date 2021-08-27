locals {
    soad_extra_environment_variables = {
        "artifacts" = local.default_database_envs
        "artifacts_query" = local.default_database_envs
        "auth" = {}
        "synchronizer" = {}
        "versions" = local.default_database_envs
        "versions_query" = local.default_database_envs
    }
    default_database_envs = {
        "POSTGRES_URL" = {
            value = var.postgres_service.host
        }
        "POSTGRES_USERNAME" = {
            value = var.postgres_service.username
        }
        "POSTGRES_DB" = {
            value = var.postgres_service.db
        }
        "POSTGRES_HOST" = {
            value = var.postgres_service.host
        }
    }
    default_secret_based_envs = {
        "POSTGRES_PASSWORD" = {
            name = var.postgres_service.password_name
            key = var.postgres_service.password_key
        }
    }
    secret_based_envs = {
        "artifacts" = local.default_secret_based_envs
        "artifacts_query" = local.default_secret_based_envs
        "auth" = {}
        "synchronizer" = {}
        "versions" = local.default_secret_based_envs
        "versions_query" = local.default_secret_based_envs
    }
    extra_configs = {
        "artifacts" = {}
        "artifacts_query" = {}
        "auth" = <<-EOF
        systemofadownload.auth {
            use-dummy-ldap = true
            expiration = "10d"
        }
        EOF
        "synchronizer" = {}
        "versions" = local.default_database_envs
        "versions_query" = local.default_database_envs
    }
    application_classpaths = {
        "auth" = "/etc/lagom/app/systemofadownload-artifact-api-1.0-SNAPSHOT.jar:/etc/lagom/lib/lagom-javadsl-api_2.13-1.6.5.jar:/etc/lagom/lib/lagom-api_2.13-1.6.5.jar:/etc/lagom/lib/play-java_2.13-2.8.8.jar:/etc/lagom/lib/typetools-0.5.0.jar:/etc/lagom/lib/play-guice_2.13-2.8.8.jar:/etc/lagom/lib/guice-assistedinject-4.2.3.jar:/etc/lagom/lib/lagom-javadsl-jackson_2.13-1.6.5.jar:/etc/lagom/lib/vavr-0.10.3.jar:/etc/lagom/lib/vavr-match-0.10.3.jar:/etc/lagom/lib/maven-artifact-3.8.1.jar:/etc/lagom/lib/plexus-utils-3.2.1.jar:/etc/lagom/lib/commons-lang3-3.9.jar:/etc/lagom/lib/systemofadownload-server-auth-1.0-SNAPSHOT.jar:/etc/lagom/lib/pac4j-http-3.7.0.jar:/etc/lagom/lib/pac4j-jwt-3.7.0.jar:/etc/lagom/lib/nimbus-jose-jwt-6.0.2.jar:/etc/lagom/lib/jcip-annotations-1.0-1.jar:/etc/lagom/lib/json-smart-2.3.jar:/etc/lagom/lib/accessors-smart-1.2.jar:/etc/lagom/lib/bcprov-jdk15on-1.59.jar:/etc/lagom/lib/play-akka-http-server_2.13-2.8.8.jar:/etc/lagom/lib/play-server_2.13-2.8.8.jar:/etc/lagom/lib/play-streams_2.13-2.8.8.jar:/etc/lagom/lib/akka-http-core_2.13-10.1.14.jar:/etc/lagom/lib/akka-parsing_2.13-10.1.14.jar:/etc/lagom/lib/lagom-javadsl-server_2.13-1.6.5.jar:/etc/lagom/lib/lagom-akka-management-javadsl_2.13-1.6.5.jar:/etc/lagom/lib/lagom-akka-management-core_2.13-1.6.5.jar:/etc/lagom/lib/lagom-server_2.13-1.6.5.jar:/etc/lagom/lib/akka-management_2.13-1.0.5.jar:/etc/lagom/lib/javax.annotation-api-1.3.2.jar:/etc/lagom/lib/systemofadownload-sonatype-1.0-SNAPSHOT.jar:/etc/lagom/lib/jackson-annotations-2.11.4.jar:/etc/lagom/lib/jackson-databind-2.11.4.jar:/etc/lagom/lib/jackson-core-2.11.4.jar:/etc/lagom/lib/lagom-javadsl-akka-discovery-service-locator_2.13-1.6.5.jar:/etc/lagom/lib/scala-library-2.13.5.jar:/etc/lagom/lib/lagom-akka-discovery-service-locator-core_2.13-1.6.5.jar:/etc/lagom/lib/scala-java8-compat_2.13-0.9.1.jar:/etc/lagom/lib/lagom-javadsl-client_2.13-1.6.5.jar:/etc/lagom/lib/lagom-client_2.13-1.6.5.jar:/etc/lagom/lib/lagom-spi_2.13-1.6.5.jar:/etc/lagom/lib/play-ws_2.13-2.8.8.jar:/etc/lagom/lib/play-ws-standalone_2.13-2.1.3.jar:/etc/lagom/lib/play-ws-standalone-xml_2.13-2.1.3.jar:/etc/lagom/lib/play-ws-standalone-json_2.13-2.1.3.jar:/etc/lagom/lib/play-ahc-ws_2.13-2.8.8.jar:/etc/lagom/lib/play-ahc-ws-standalone_2.13-2.1.3.jar:/etc/lagom/lib/cachecontrol_2.13-2.0.0.jar:/etc/lagom/lib/shaded-asynchttpclient-2.1.3.jar:/etc/lagom/lib/shaded-oauth-2.1.3.jar:/etc/lagom/lib/cache-api-1.1.1.jar:/etc/lagom/lib/metrics-core-3.2.6.jar:/etc/lagom/lib/netty-reactive-streams-2.0.5.jar:/etc/lagom/lib/netty-codec-http-4.1.63.Final.jar:/etc/lagom/lib/netty-common-4.1.63.Final.jar:/etc/lagom/lib/netty-buffer-4.1.63.Final.jar:/etc/lagom/lib/netty-transport-4.1.63.Final.jar:/etc/lagom/lib/netty-codec-4.1.63.Final.jar:/etc/lagom/lib/netty-handler-4.1.63.Final.jar:/etc/lagom/lib/netty-resolver-4.1.63.Final.jar:/etc/lagom/lib/lagom-javadsl-persistence-jpa_2.13-1.6.5.jar:/etc/lagom/lib/lagom-javadsl-persistence-jdbc_2.13-1.6.5.jar:/etc/lagom/lib/lagom-persistence-jdbc-core_2.13-1.6.5.jar:/etc/lagom/lib/akka-persistence-jdbc_2.13-3.5.3.jar:/etc/lagom/lib/slick_2.13-3.3.2.jar:/etc/lagom/lib/slick-hikaricp_2.13-3.3.2.jar:/etc/lagom/lib/play-jdbc_2.13-2.8.8.jar:/etc/lagom/lib/play-jdbc-api_2.13-2.8.8.jar:/etc/lagom/lib/HikariCP-3.4.5.jar:/etc/lagom/lib/jdbcdslog-1.0.6.2.jar:/etc/lagom/lib/tyrex-1.0.1.jar:/etc/lagom/lib/lagom-javadsl-persistence_2.13-1.6.5.jar:/etc/lagom/lib/lagom-javadsl-projection_2.13-1.6.5.jar:/etc/lagom/lib/byte-buddy-1.10.5.jar:/etc/lagom/lib/jnr-constants-0.9.14.jar:/etc/lagom/lib/lagom-javadsl-kafka-broker_2.13-1.6.5.jar:/etc/lagom/lib/lagom-javadsl-broker_2.13-1.6.5.jar:/etc/lagom/lib/lagom-kafka-broker_2.13-1.6.5.jar:/etc/lagom/lib/lagom-projection-core_2.13-1.6.5.jar:/etc/lagom/lib/lagom-kafka-client_2.13-1.6.5.jar:/etc/lagom/lib/akka-stream-kafka_2.13-1.1.0.jar:/etc/lagom/lib/kafka-clients-2.1.1.jar:/etc/lagom/lib/zstd-jni-1.3.7-1.jar:/etc/lagom/lib/snappy-java-1.1.7.2.jar:/etc/lagom/lib/scala-collection-compat_2.13-2.1.2.jar:/etc/lagom/lib/lagom-javadsl-kafka-client_2.13-1.6.5.jar:/etc/lagom/lib/slf4j-api-1.7.30.jar:/etc/lagom/lib/log4j-1.2.17.jar:/etc/lagom/lib/jnr-posix-3.0.50.jar:/etc/lagom/lib/lagom-logback_2.13-1.6.5.jar:/etc/lagom/lib/jcl-over-slf4j-1.7.30.jar:/etc/lagom/lib/jul-to-slf4j-1.7.30.jar:/etc/lagom/lib/log4j-over-slf4j-1.7.30.jar:/etc/lagom/lib/play_2.13-2.8.8.jar:/etc/lagom/lib/build-link-2.8.8.jar:/etc/lagom/lib/play-exceptions-2.8.8.jar:/etc/lagom/lib/twirl-api_2.13-1.5.1.jar:/etc/lagom/lib/jjwt-0.9.1.jar:/etc/lagom/lib/jakarta.xml.bind-api-2.3.3.jar:/etc/lagom/lib/jakarta.activation-api-1.2.2.jar:/etc/lagom/lib/jakarta.transaction-api-1.3.3.jar:/etc/lagom/lib/reactive-streams-1.0.3.jar:/etc/lagom/lib/ssl-config-core_2.13-0.4.2.jar:/etc/lagom/lib/config-1.4.0.jar:/etc/lagom/lib/play-json_2.13-2.9.1.jar:/etc/lagom/lib/play-functional_2.13-2.9.1.jar:/etc/lagom/lib/scala-reflect-2.13.2.jar:/etc/lagom/lib/scala-parser-combinators_2.13-1.1.2.jar:/etc/lagom/lib/akka-stream_2.13-2.6.14.jar:/etc/lagom/lib/akka-stream-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-actor_2.13-2.6.14.jar:/etc/lagom/lib/akka-actor-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-serialization-jackson_2.13-2.6.14.jar:/etc/lagom/lib/lz4-java-1.7.1.jar:/etc/lagom/lib/akka-slf4j_2.13-2.6.14.jar:/etc/lagom/lib/akka-protobuf-v3_2.13-2.6.14.jar:/etc/lagom/lib/scala-xml_2.13-1.2.0.jar:/etc/lagom/lib/guava-28.2-jre.jar:/etc/lagom/lib/failureaccess-1.0.1.jar:/etc/lagom/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/etc/lagom/lib/jsr305-3.0.2.jar:/etc/lagom/lib/error_prone_annotations-2.3.4.jar:/etc/lagom/lib/j2objc-annotations-1.3.jar:/etc/lagom/lib/jnr-ffi-2.1.10.jar:/etc/lagom/lib/jffi-1.2.19-native.jar:/etc/lagom/lib/jnr-x86asm-1.0.2.jar:/etc/lagom/lib/jffi-1.2.22.jar:/etc/lagom/lib/jnr-a64asm-1.0.0.jar:/etc/lagom/lib/pcollections-3.0.5.jar:/etc/lagom/lib/jackson-datatype-jdk8-2.11.4.jar:/etc/lagom/lib/jackson-datatype-jsr310-2.11.4.jar:/etc/lagom/lib/jackson-datatype-guava-2.11.4.jar:/etc/lagom/lib/jackson-datatype-pcollections-2.11.4.jar:/etc/lagom/lib/jackson-module-parameter-names-2.11.4.jar:/etc/lagom/lib/jackson-module-paranamer-2.11.4.jar:/etc/lagom/lib/paranamer-2.8.jar:/etc/lagom/lib/jackson-module-scala_2.13-2.11.4.jar:/etc/lagom/lib/jackson-dataformat-cbor-2.11.4.jar:/etc/lagom/lib/asm-7.2.jar:/etc/lagom/lib/asm-analysis-7.2.jar:/etc/lagom/lib/asm-commons-7.2.jar:/etc/lagom/lib/asm-tree-7.2.jar:/etc/lagom/lib/asm-util-7.2.jar:/etc/lagom/lib/logback-core-1.2.3.jar:/etc/lagom/lib/logback-classic-1.2.3.jar:/etc/lagom/lib/guice-5.0.1.jar:/etc/lagom/lib/javax.inject-1.jar:/etc/lagom/lib/aopalliance-1.0.jar:/etc/lagom/lib/hibernate-core-5.5.4.Final.jar:/etc/lagom/lib/jboss-logging-3.4.2.Final.jar:/etc/lagom/lib/javax.persistence-api-2.2.jar:/etc/lagom/lib/javassist-3.24.0-GA.jar:/etc/lagom/lib/antlr-2.7.7.jar:/etc/lagom/lib/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/etc/lagom/lib/jandex-2.2.3.Final.jar:/etc/lagom/lib/classmate-1.5.1.jar:/etc/lagom/lib/javax.activation-api-1.2.0.jar:/etc/lagom/lib/hibernate-commons-annotations-5.1.2.Final.jar:/etc/lagom/lib/jaxb-api-2.3.1.jar:/etc/lagom/lib/jaxb-runtime-2.3.1.jar:/etc/lagom/lib/txw2-2.3.1.jar:/etc/lagom/lib/istack-commons-runtime-3.0.7.jar:/etc/lagom/lib/stax-ex-1.8.jar:/etc/lagom/lib/FastInfoset-1.2.15.jar:/etc/lagom/lib/postgresql-42.2.18.jar:/etc/lagom/lib/checker-qual-2.10.0.jar:/etc/lagom/lib/vavr-jackson-0.10.3.jar:/etc/lagom/lib/lagom-pac4j_2.13-2.2.1.jar:/etc/lagom/lib/pac4j-core-3.7.0.jar:/etc/lagom/lib/lagom-javadsl-testkit_2.13-1.6.5.jar:/etc/lagom/lib/lagom-core-testkit_2.13-1.6.5.jar:/etc/lagom/lib/lagom-persistence-testkit_2.13-1.6.5.jar:/etc/lagom/lib/akka-persistence-cassandra-launcher_2.13-0.106.jar:/etc/lagom/lib/lagom-javadsl-pubsub_2.13-1.6.5.jar:/etc/lagom/lib/lagom-cluster-core_2.13-1.6.5.jar:/etc/lagom/lib/akka-cluster_2.13-2.6.14.jar:/etc/lagom/lib/akka-cluster-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-management-cluster-bootstrap_2.13-1.0.5.jar:/etc/lagom/lib/akka-management-cluster-http_2.13-1.0.5.jar:/etc/lagom/lib/akka-distributed-data_2.13-2.6.14.jar:/etc/lagom/lib/lmdbjava-0.7.0.jar:/etc/lagom/lib/lagom-javadsl-cluster_2.13-1.6.5.jar:/etc/lagom/lib/akka-cluster-tools_2.13-2.6.14.jar:/etc/lagom/lib/akka-coordination_2.13-2.6.14.jar:/etc/lagom/lib/lagom-dev-mode-ssl-support_2.13-1.6.5.jar:/etc/lagom/lib/lagom-persistence-core_2.13-1.6.5.jar:/etc/lagom/lib/akka-persistence_2.13-2.6.14.jar:/etc/lagom/lib/akka-persistence-query_2.13-2.6.14.jar:/etc/lagom/lib/akka-cluster-sharding_2.13-2.6.14.jar:/etc/lagom/lib/akka-cluster-sharding-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-stream-testkit_2.13-2.6.14.jar:/etc/lagom/lib/junit-4.13.2.jar:/etc/lagom/lib/hamcrest-core-1.3.jar:/etc/lagom/lib/akka-persistence-testkit_2.13-2.6.14.jar:/etc/lagom/lib/akka-persistence-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-remote_2.13-2.6.14.jar:/etc/lagom/lib/akka-pki_2.13-2.6.14.jar:/etc/lagom/lib/asn-one-0.5.0.jar:/etc/lagom/lib/agrona-1.9.0.jar:/etc/lagom/lib/akka-testkit_2.13-2.6.14.jar:/etc/lagom/lib/akka-actor-testkit-typed_2.13-2.6.14.jar:/etc/lagom/lib/akka-discovery-kubernetes-api_2.13-1.0.3.jar:/etc/lagom/lib/akka-discovery_2.13-2.6.14.jar:/etc/lagom/lib/akka-http_2.13-10.1.14.jar:/etc/lagom/lib/akka-http-spray-json_2.13-10.1.14.jar:/etc/lagom/lib/spray-json_2.13-1.3.5.jar:/etc/lagom/lib/junit-jupiter-api-5.7.2.jar:/etc/lagom/lib/apiguardian-api-1.1.0.jar:/etc/lagom/lib/opentest4j-1.2.0.jar"

    }
}
