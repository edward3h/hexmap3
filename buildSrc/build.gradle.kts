plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.20.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.20.0")
    implementation("io.micronaut:micronaut-jackson-databind:4.0.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
        formatAnnotations()
        cleanthat()
    }
    kotlinGradle {
        ktlint()
    }
    yaml {
        target("**/*.yml", "**/*.yaml")
        jackson()
    }
}
