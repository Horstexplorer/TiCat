package de.hypercdn.ticat.server.config

import lombok.extern.log4j.Log4j2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.core.task.SimpleAsyncTaskExecutor


@Configuration
class AsyncEventDispatcherConfiguration {

    companion object {
        var log: Logger = LoggerFactory.getLogger(AsyncEventDispatcherConfiguration::class.java)
    }

    @Bean
    fun applicationEventMulticaster(): ApplicationEventMulticaster {
        return SimpleApplicationEventMulticaster().apply {
            setTaskExecutor(SimpleAsyncTaskExecutor())
            setErrorHandler {
                log.error("Unhandled exception occurred while handling events", it)
            }
        }
    }

}