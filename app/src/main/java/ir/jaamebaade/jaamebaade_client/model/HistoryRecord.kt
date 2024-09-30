package ir.jaamebaade.jaamebaade_client.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history",
    foreignKeys = [
        ForeignKey(
            entity = Poem::class,
            parentColumns = ["id"],
            childColumns = ["poem_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("poem_id")]
)
data class HistoryRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "poem_id") val poemId: Int,
    val timestamp: Long,
)
