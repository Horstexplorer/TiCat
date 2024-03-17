package de.hypercdn.ticat.server.data.sql.base.audit

interface AuditAttachment<E> where E : Audit<*, E, *>{
}