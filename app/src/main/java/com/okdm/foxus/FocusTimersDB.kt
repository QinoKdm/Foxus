package com.okdm.foxus

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "FocusTimers")
data class FOCUS_TIMERS_ENTITY(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "focus_time") val focusTime: Int,
    @ColumnInfo(name = "rest_time") val restTime: Int
)

@Dao
interface FocusTimersDao{
    @Query("select * from FocusTimers")
    fun getAll(): List<FOCUS_TIMERS_ENTITY>

    @Insert
    fun insertNewTimer(focusTimersEntity: FOCUS_TIMERS_ENTITY)

    @Query("select * from FocusTimers where name = :name")
    fun getInfoByName(name: String): List<FOCUS_TIMERS_ENTITY>
}

@Database(
    version = 1,
    entities = [FOCUS_TIMERS_ENTITY::class]
)
abstract class FocusTimersDatabase: RoomDatabase(){
    companion object{
        private var DB: FocusTimersDatabase ?= null
        private val name = "FocusTimers"

        fun getDB(context: Context) = if(DB == null){
            Room.databaseBuilder(context, FocusTimersDatabase::class.java, "FocusTimersDatabase").build().apply {
                DB = this
            }
        }
        else{
            DB!!
        }
    }

    abstract fun getFocusTimerDao(): FocusTimersDao
}