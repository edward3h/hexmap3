import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.version

plugins {
    java
    id("com.diffplug.spotless")
}

repositories {
    mavenCentral()
}
dependencies {

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
