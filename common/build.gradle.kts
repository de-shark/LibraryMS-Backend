dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.commons:commons-lang3")
}

tasks.named("bootJar") {
    enabled = false
}