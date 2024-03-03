package de.hypercdn.ticat.server.data.sql.entities.page.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.history.History
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "page_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class PageHistory : History<PageHistory>, CopyConstructable<PageHistory> {

    companion object

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "entity_reference_uuid",
        referencedColumnName = "page_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var page: Page

    @Column(
        name = "old_title",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldTitle: String? = null

    @Column(
        name = "old_content",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldContent: String? = null

    @Column(
        name = "old_setting_status",
        updatable = false,
        nullable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldSettingStatus: Page.Settings.Status? = null

    @Column(
        name = "old_setting_parent_page_uuid",
        updatable = false,
        nullable = false
    )
    @ColumnDefault("NULL")
    var oldSettingParentPageUUID: UUID? = null

    constructor()

    constructor(other: PageHistory): super(other){
        this.oldTitle = other.oldTitle
        this.oldContent = other.oldContent
        this.oldSettingStatus = other.oldSettingStatus
    }

}