package com.example.teamkuy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teamkuy.databinding.FragmentLoginBinding

class LoginActivity : AppCompatActivity() {

private lateinit var binding: FragmentLoginBinding

    lateinit var email : EditText
    lateinit var pass: EditText
    lateinit var button: Button
    lateinit var buttonr: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        email = binding.inputEmail
        pass = binding.inputPass
        button = binding.btnMasuk
        buttonr = binding.daftarAkun

        binding.btnMasuk.setOnClickListener {
            val username = email.text.toString()
            val password = pass.text.toString()

            if (username == "user123" && password == "password123") {
                // Login berhasil, alihkan ke aktivitas lain (misalnya, MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Login gagal, berikan pesan kesalahan
                Toast.makeText(this, "Login gagal. Periksa username dan password.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.daftarAkun.setOnClickListener {
            // Tambahkan logika untuk mengarahkan pengguna ke form registrasi
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

