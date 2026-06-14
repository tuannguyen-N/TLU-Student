package org.example.project.data.local

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `chat_messages` (
                `id` TEXT NOT NULL,
                `roomId` TEXT NOT NULL,
                `senderId` TEXT NOT NULL,
                `text` TEXT,
                `fileUrl` TEXT,
                `fileName` TEXT,
                `fileSize` TEXT,
                `type` TEXT NOT NULL,
                `timestamp` INTEGER NOT NULL,
                `senderType` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
        connection.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_chat_messages_roomId_timestamp` ON `chat_messages` (`roomId`, `timestamp`)"
        )
    }
}
