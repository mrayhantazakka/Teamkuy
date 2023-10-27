package com.example.teamkuy.ui.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM Note WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Note>

    @Query("SELECT * FROM Note WHERE NamaPengguna = :NamaPengguna AND Password = :password")
    fun checkUserPass(NamaPengguna: String, password: String): List<Note>

    @Query("SELECT * FROM Note WHERE id = :userId")
    fun getUserById(userId: Int): Note

    @Query("SELECT * FROM Note WHERE NamaPengguna = :NamaPengguna")
    fun getUserByUsername(NamaPengguna: String): Note

    @Query("SELECT * FROM Note WHERE Email = :email")
    fun getUserByEmail(email: String): Note

    @Insert
    fun insert(vararg users: Note): List<Long>

    @Delete
    fun delete(user: Note)
}
