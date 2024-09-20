plugins {
    id("java")
    id("com.diffplug.spotless") version "7.0.0.BETA2"
}

group = "com.erabsolute.discord.bots"
version = "2.0.0"
val jdaVersion = "5.1.0"
val apacheCommonsVersion = "3.17.0"
val zxingVersion = "3.5.3"
val googleJavaFormatVersion = "1.23.0"
val mainClassName = "com.erabsolute.discord.bots.App"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:$apacheCommonsVersion")
    implementation("com.google.zxing:core:$zxingVersion")
    implementation("com.google.zxing:javase:$zxingVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

spotless {
    java {
        importOrder()
        formatAnnotations()  // fixes formatting of type annotations
        toggleOffOn()
        googleJavaFormat(googleJavaFormatVersion)
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = mainClassName
    }
}