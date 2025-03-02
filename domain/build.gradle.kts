dependencies {
    implementation(project(":common"))

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks.named("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()  // 让 Gradle 运行 JUnit 5 测试
}