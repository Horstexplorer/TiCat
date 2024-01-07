package de.hypercdn.ticat.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TiCatServerApplication

fun main(args: Array<String>) {
	runApplication<TiCatServerApplication>(*args)
}
