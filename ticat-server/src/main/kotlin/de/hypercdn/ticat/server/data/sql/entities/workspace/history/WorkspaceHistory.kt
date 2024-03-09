package de.hypercdn.ticat.server.data.sql.entities.workspace.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import de.hypercdn.ticat.server.data.sql.base.history.History
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "workspace_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class WorkspaceHistory : History<Workspace, WorkspaceHistory>{

    companion object

    @Column(
        name = "old_title",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldTitle: String? = null

    @Column(
        name = "old_description",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldDescription: String? = null

    @Column(
        name = "old_setting_visibility",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldVisibility: Workspace.Settings.Visibility? = null

    @Column(
        name = "old_setting_access_mode",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldAccessMode: Workspace.Settings.AccessMode? = null

    @Column(
        name = "old_setting_status",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldStatus: Workspace.Settings.Status? = null

    constructor(): super()

    constructor(other: WorkspaceHistory): super(other) {
        this.oldTitle = other.oldTitle
        this.oldDescription = other.oldDescription
        this.oldVisibility = other.oldVisibility
        this.oldAccessMode = other.oldAccessMode
        this.oldStatus = other.oldStatus
    }

}