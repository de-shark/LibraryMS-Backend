dependencies {
    implementation(project(":common"))

    implementation("com.github.f4b6a3:uuid-creator:6.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.mockito:mockito-core:5.15.2")
}

tasks.named("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()  // 让 Gradle 运行 JUnit 5 测试
}