plugins {
    `java-convention`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.0.1"
    id("io.micronaut.test-resources") version "4.0.1"
//    id("io.micronaut.aot") version "4.0.1"
}

version = "0.1"
group = "us.ordoacerb.hexmap"

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-oauth2")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut:micronaut-jackson-databind")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
//    aotPlugins("io.micronaut.security:micronaut-security-aot:4.0.1")
}

application {
    mainClass.set("us.ordoacerb.hexmap.Application")
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("us.ordoacerb.hexmap.*")
    }
//    aot {
//    // Please review carefully the optimizations enabled below
//    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
//        optimizeServiceLoading.set(false)
//        convertYamlToJava.set(false)
//        precomputeOperations.set(true)
//        cacheEnvironment.set(true)
//        optimizeClassLoading.set(true)
//        deduceEnvironment.set(true)
//        optimizeNetty.set(true)
//        configurationProperties.put("micronaut.security.jwks.enabled","false")
//        configurationProperties.put("micronaut.security.openid-configuration.enabled","false")
//    }
}
