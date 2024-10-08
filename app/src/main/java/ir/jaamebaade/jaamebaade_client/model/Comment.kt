package ir.jaamebaade.jaamebaade_client.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [ForeignKey(
        entity = Poem::class,
        parentColumns = ["id"],
        childColumns = ["poem_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["poem_id"])]
)
data class Comment(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "poem_id") val poemId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)