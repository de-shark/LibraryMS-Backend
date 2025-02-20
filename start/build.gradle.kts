plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":interfaces"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}

springBoot {
    mainClass.set("me.deshark.lms.LmsApplication")
}

tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = false
}