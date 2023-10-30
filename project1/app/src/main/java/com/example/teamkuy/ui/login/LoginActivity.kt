package com.example.teamkuy.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.database.NoteRoomDatabase
import com.example.teamkuy.MainActivity
import com.example.teamkuy.RegisterActivity
import com.example.teamkuy.databinding.FragmentLoginBinding
import com.example.teamkuy.ui.database.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

private lateinit var binding: FragmentLoginBinding

    private lateinit var db: NoteRoomDatabase
    lateinit var email : EditText
    lateinit var pass: EditText
    lateinit var button: Button
    lateinit var buttonr: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        db = NoteRoomDatabase.getDatabase(this)

        email = binding.inputEmail
        pass = binding.inputPass
        button = binding.btnMasuk
        buttonr = binding.daftarAkun

        binding.btnMasuk.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userDao = db.NoteDao()

                // Jalankan operasi login dengan Kotlin Coroutines
                GlobalScope.launch {
                    val Note = userDao.checkUserPass(email, password)

                    if (Note.isNotEmpty()) {

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        // Gagal login
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Login Gagal. Cek kembali username dan password.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
//        txtDaftarListener()
//    }
        fun saveLoginStatus() {
            val sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", true)
            editor.apply()
        }

        // Fungsi untuk menyimpan data pengguna ke SharedPreferences
        fun saveUserData(userId: Int, username: String, email: String) {
            val sharedPreferences = getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Simpan data pengguna
            editor.putInt("user_id", userId)
            editor.putString("username", username)
            editor.putString("email", email)
            editor.apply()
        }
        binding.daftarAkun.setOnClickListener {
            // Tambahkan logika untuk mengarahkan pengguna ke form registrasi
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

