package de.hypercdn.ticat.server.data.sql.entities.page.history

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.sql.base.history.History
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "page_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class PageHistory : History<Page, PageHistory> {

    companion object

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

    constructor(): super()

    constructor(other: PageHistory): super(other){
        this.oldTitle = other.oldTitle
        this.oldContent = other.oldContent
        this.oldSettingStatus = other.oldSettingStatus
    }

}