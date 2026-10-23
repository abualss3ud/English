package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordProgressDao {
    @Query("SELECT * FROM word_progress")
    fun getAllProgress(): Flow<List<WordProgressEntity>>

    @Query("SELECT * FROM word_progress WHERE wordId = :id")
    suspend fun getProgressForWord(id: Int): WordProgressEntity?

    @Query("SELECT * FROM word_progress WHERE isFavorite = 1")
    fun getFavoriteWords(): Flow<List<WordProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: WordProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<WordProgressEntity>)

    @Query("UPDATE word_progress SET isFavorite = :isFav WHERE wordId = :id")
    suspend fun updateFavorite(id: Int, isFav: Boolean)

    @Query("DELETE FROM word_progress")
    suspend fun clearAll()
}

@Dao
interface DailyLessonDao {
    @Query("SELECT * FROM daily_lessons ORDER BY date DESC")
    fun getAllLessons(): Flow<List<DailyLessonEntity>>

    @Query("SELECT * FROM daily_lessons WHERE date = :date")
    suspend fun getLessonForDate(date: String): DailyLessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(lesson: DailyLessonEntity)

    @Query("DELETE FROM daily_lessons")
    suspend fun clearAll()
}

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getSettingsFlow(): Flow<UserSettingsEntity?>

    @Query("SELECT * FROM user_settings WHERE id = 1")
    suspend fun getSettings(): UserSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettingsEntity)

    @Query("DELETE FROM user_settings")
    suspend fun clearAll()
}
