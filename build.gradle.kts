plugins {
    id("com.diffplug.spotless")
}
repositories {
    mavenCentral()
}

spotless {
    kotlinGradle {
        ktlint()
    }
    yaml {
        target("**/*.yml", "**/*.yaml")
        jackson()
    }
}
