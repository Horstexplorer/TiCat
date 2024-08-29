package de.hypercdn.ticat.server.data.sql.entities.workspace

fun Workspace.isDeleted(): Boolean = this.settings.status == Workspace.Settings.Status.DELETED

fun Workspace.isArchived(): Boolean = this.settings.status == Workspace.Settings.Status.ARCHIVED