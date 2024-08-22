/**
 * Dependencies versions
 *
 * @author Tang
 */
class Versions {
    // https://mvnrepository.com/artifact/org.yaml/snakeyaml
    val snakeyaml = "2.2"
    // https://mvnrepository.com/artifact/org.apache.derby/derby
    val derby = "10.16.1.1"
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    val slf4j = "2.0.13"
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    val logback = "1.5.6"
    // https://mvnrepository.com/artifact/org.springframework/spring-core
    val spring = "6.1.11"
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot
    val springBoot = "3.3.2"
}

class Libraries {
    val snakeyaml = "org.yaml:snakeyaml:${versions.snakeyaml}"
    val derby = "org.apache.derby:derby:${versions.derby}"
    val derbytools = "org.apache.derby:derbytools:${versions.derby}"
    val slf4j = "org.slf4j:slf4j-api:${versions.slf4j}"
    val logback = "ch.qos.logback:logback-classic:${versions.logback}"
    val springContext = "org.springframework:spring-context:${versions.spring}"
    val springBootStarter = "org.springframework.boot:spring-boot-starter:${versions.springBoot}"
    val springBootStarterJdbc = "org.springframework.boot:spring-boot-starter-jdbc:${versions.springBoot}"
    val springBootAutoconfigure = "org.springframework.boot:spring-boot-autoconfigure:${versions.springBoot}"
    val springBootAutoconfigureProcessor = "org.springframework.boot:spring-boot-autoconfigure-processor:${versions.springBoot}"
    val springBootStarterTest = "org.springframework.boot:spring-boot-starter-test:${versions.springBoot}"
}

val versions = Versions()
val libs = Libraries()
