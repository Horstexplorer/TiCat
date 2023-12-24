package de.hypercdn.ticat.server.data.json.entities.user

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.user.User

class UserResponseJsonBuilder(
    private val user: User? = null
): EntityTemplateBuilder<UserResponseJson>({ UserResponseJson() }) {

}

fun UserResponseJson.Companion.builder(user: User? = null): UserResponseJsonBuilder = UserResponseJsonBuilder(user)
fun User.toJsonResponseBuilder(): UserResponseJsonBuilder = UserResponseJsonBuilder(this)