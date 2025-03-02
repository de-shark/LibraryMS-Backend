dependencies {
    implementation(project(":common"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.mockito:mockito-core:5.15.2")
}

tasks.named("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()  // 让 Gradle 运行 JUnit 5 测试
}