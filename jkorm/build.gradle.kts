plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.tang"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.yaml/snakeyaml
    implementation("org.yaml:snakeyaml:2.2")
    // https://mvnrepository.com/artifact/org.apache.derby/derby
    implementation("org.apache.derby:derby:10.14.2.0")
    // https://mvnrepository.com/artifact/org.apache.derby/derbytools
    implementation("org.apache.derby:derbytools:10.14.2.0")
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:8.3.0")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.13")
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    testImplementation("ch.qos.logback:logback-classic:1.3.14")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}
