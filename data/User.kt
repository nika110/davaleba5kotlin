package ge.edu.btu.davaleba5kotlin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "run_range") val runRange: Double,
    @ColumnInfo(name = "swim_range") val swimRange: Double,
    @ColumnInfo(name = "calorie") val calorie: Double,
)
