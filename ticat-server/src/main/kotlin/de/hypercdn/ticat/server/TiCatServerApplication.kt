package de.hypercdn.ticat.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TicatServerApplication

fun main(args: Array<String>) {
	runApplication<TicatServerApplication>(*args)
}
