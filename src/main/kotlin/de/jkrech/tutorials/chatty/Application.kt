package de.jkrech.tutorials.chatty

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["de.jkrech.tutorials"])
class Application
@Suppress("detekt:SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}