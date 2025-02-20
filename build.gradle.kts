plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
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
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    if (project.name != "domain") {
        dependencies {
            implementation("org.springframework.boot:spring-boot-starter")
            implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:3.4.2"))
        }
    }

}