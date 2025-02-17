plugins {
    id("java")
    id("org.springframework.boot") version "3.2.3" apply false
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
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    if (project.name != "domain") {
        dependencies {
//            implementation("org.springframework.boot:spring-boot-starter")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}