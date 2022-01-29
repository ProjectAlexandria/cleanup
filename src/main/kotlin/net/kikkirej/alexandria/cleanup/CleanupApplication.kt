package net.kikkirej.alexandria.cleanup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CleanupApplication

fun main(args: Array<String>) {
	runApplication<CleanupApplication>(*args)
}
