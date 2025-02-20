dependencies {
    implementation(project(":interfaces"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

springBoot {
    mainClass.set("me.deshark.lms.LmsApplication")
}

tasks.named("bootJar") {
    enabled = true
}