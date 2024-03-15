package de.hypercdn.ticat.server

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.data.sql.entities.message.history.asHistoryFromOriginal
import de.hypercdn.ticat.server.helper.modifyWithContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TiCatServerApplication

fun main(args: Array<String>) {

	var a = Message()
		.apply {
			content = "lol"
		}.modifyWithContext().modifyCopy {
			it.settings.status = Message.Settings.Status.DELETED
		}.asHistoryFromOriginal()
	println(a)


	//runApplication<TiCatServerApplication>(*args)
}
