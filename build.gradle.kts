plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.sekiya"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Hytale API - Using provided scope as it will be available at runtime
    compileOnly(fileTree("libs") { include("*.jar") })
    
    // javax.annotation for @Nonnull
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    
    // JSON processing
    implementation("com.google.code.gson:gson:2.10.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveFileName.set("SekiyaDungeons-${project.version}.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
