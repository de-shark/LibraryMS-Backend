plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3" apply false
}

allprojects {
    group = "me.deshark.lms"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    }

    if (project.name == "start") {
        apply(plugin = "org.springframework.boot")

        dependencies {
            implementation("org.springframework.boot:spring-boot-starter")
        }
    }
}