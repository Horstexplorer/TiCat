package de.hypercdn.ticat.server.data.sql.entities.page

fun Page.isDeleted(): Boolean = this.settings.status == Page.Settings.Status.DELETED

fun Page.isArchived(): Boolean = this.settings.status == Page.Settings.Status.ARCHIVED