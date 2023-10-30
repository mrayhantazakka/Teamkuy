package com.example.teamkuy.ui.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Note")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "GitHubUsername")
    var GitHubUsername: String? = null,

    @ColumnInfo(name = "Email")
    var Email: String? = null,

    @ColumnInfo(name = "NamaPengguna")
    var NamaPengguna: String? = null,

    @ColumnInfo(name = "Password")
    var Password: String? = null

) : Parcelable