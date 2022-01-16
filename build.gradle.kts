plugins {
    id("org.jetbrains.intellij") version "1.3.0"
    kotlin("jvm") version "1.6.10"
    java
}

group = "fr.o80"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

intellij {
    version.set("2021.2.4")
}

tasks {
    patchPluginXml {
        changeNotes.set("""
            <h1>Dev</h1>
            <ul>
                <li>See all the versions in one screen</li>
                <li>Update them manually</li>
            </ul>""".trimIndent())
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
