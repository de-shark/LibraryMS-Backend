dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.5")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator
    implementation("com.github.f4b6a3:uuid-creator:6.0.0")

} 