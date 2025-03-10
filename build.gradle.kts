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
    apply(plugin = "org.springframework.boot")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    }

    // 按需排除特定模块
    if (project.name == "domain") {
        tasks.named("bootJar") {
            enabled = false
        }
        tasks.named("jar") {
            enabled = true
        }
    } else {
        dependencies {
            implementation("org.springframework.boot:spring-boot-starter")
        }
    }
}