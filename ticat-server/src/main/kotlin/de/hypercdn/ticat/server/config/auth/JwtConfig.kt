package de.hypercdn.ticat.server.config.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
class JwtConfig {

    var roleMapping: RoleMapping = RoleMapping()

    class RoleMapping {
        lateinit var accountTypeAdmin: String
        lateinit var accountEnable: String
    }

    var propertyMapping: PropertyMapping = PropertyMapping()

    class PropertyMapping {
        lateinit var firstName: String // given-name
        lateinit var familyName: String // last-name
        lateinit var displayName: String // preferred-username
        lateinit var email: String // email
        lateinit var roles: String // realm_access.roles
    }

}