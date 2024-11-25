package de.hypercdn.ticat.server.config.event

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.core.task.SimpleAsyncTaskExecutor


@Configuration
class AsyncEventDispatcherConfiguration {

    companion object {
        var log: KLogger = KotlinLogging.logger {}
    }

    @Bean
    fun applicationEventMulticaster(): ApplicationEventMulticaster {
        return SimpleApplicationEventMulticaster().apply {
            setTaskExecutor(SimpleAsyncTaskExecutor())
            setErrorHandler {
                log.error(it){"Unhandled exception occurred while handling events"}
            }
        }
    }

}