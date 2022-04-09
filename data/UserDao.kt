package ge.edu.btu.davaleba5kotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT AVG(run_range) FROM user")
    fun avgOfRunRange(): Double

    @Query("SELECT AVG(swim_range) FROM user")
    fun avgOfSwimRange(): Double

    @Query("SELECT AVG(calorie) FROM user")
    fun avgOfCalories(): Double

    @Query("SELECT SUM(run_range) FROM user")
    fun totalDistance(): Double

}
