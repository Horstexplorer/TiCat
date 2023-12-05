package de.hypercdn.ticat.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .anonymous { it.disable() }
            .authorizeHttpRequests {
                // no auth required
                // exposing the api documentation behind /docs
                it.requestMatchers("/docs/**").permitAll()
                // no auth or optional auth
                it.requestMatchers(
                    HttpMethod.GET,
                    ""
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.POST,
                    ""
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.PATCH,
                    ""
                ).permitAll()
                it.requestMatchers(
                    HttpMethod.DELETE,
                    ""
                ).permitAll()

                // auth required
                it.requestMatchers(
                    HttpMethod.GET,
                    ""
                ).authenticated()
                it.requestMatchers(
                    HttpMethod.POST,
                    ""
                ).authenticated()
                it.requestMatchers(
                    HttpMethod.PATCH,
                    ""
                ).authenticated()
                it.requestMatchers(
                    HttpMethod.DELETE,
                    ""
                ).authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { }
            }

        return http.build()
    }

}