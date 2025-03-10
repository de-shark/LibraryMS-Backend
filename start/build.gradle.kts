dependencies {
    implementation(project(":interfaces"))
    implementation(project(":infrastructure"))

}

springBoot {
    mainClass.set("me.deshark.lms.LmsApplication")
}