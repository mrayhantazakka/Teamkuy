package com.example.teamkuy

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.teamapp.database.NoteRoomDatabase
import com.example.teamkuy.R
import com.example.teamkuy.databinding.FragmentRegisterBinding
import com.example.teamkuy.ui.database.Note
import com.example.teamkuy.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var mBinding: FragmentRegisterBinding
    private lateinit var db: NoteRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FragmentRegisterBinding.inflate(LayoutInflater.from(this))

        setContentView(mBinding.root)
        supportActionBar?.hide()

        db = NoteRoomDatabase.getDatabase(this)

        mBinding.btndaftar.setOnClickListener {
            val githubUsername = mBinding.inputNamaPenggunaGithub.text.toString()
            val email = mBinding.inputEmail.text.toString()
            val NamaPengguna = mBinding.inputNamaPengguna.text.toString()
            val password = mBinding.inputPassword.text.toString()

            if (githubUsername.isNotEmpty() && email.isNotEmpty() && NamaPengguna.isNotEmpty() && password.isNotEmpty()) {
                val noteDao = db.NoteDao()
                val note = Note(0, githubUsername, email, NamaPengguna, password)

                // Jalankan operasi penyimpanan dengan Kotlin Coroutines
                GlobalScope.launch {
                    val useruId = noteDao.insert(note)

                    if (useruId.isNotEmpty()) {
                        // Data berhasil disimpan
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registrasi Berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        // Gagal menyimpan data
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "User Sudah Ada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        txtLoginListener()
    }

private fun txtLoginListener() {
    mBinding.backlogin.setOnClickListener {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
    private fun showError(binding: TextInputLayout, errorMessage: String) {
        binding.apply {
            isErrorEnabled = true
            error = errorMessage
        }
    }

    private fun clearError(binding: TextInputLayout) {
        binding.isErrorEnabled = false
    }

    private fun validateFullName(): Boolean {
        val value =mBinding.inputNamaPengguna.text.toString()
        if (value.isEmpty()) {
            showError(mBinding.namaPenggunaLayout, "Nama lengkap diperlukan")
            return false
        }
        clearError(mBinding.namaPenggunaLayout)
        return true
    }

    private fun validateEmail(): Boolean {
        val value = mBinding.inputEmail.text.toString()
        if (value.isEmpty()) {
            showError(mBinding.emailLayout, "Email diperlukan")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            showError(mBinding.emailLayout, "Email tidak valid")
            return false
        }
        clearError(mBinding.emailLayout)
        return true
    }

    private fun validatePassword(): Boolean {
        val value = mBinding.inputPassword.text.toString()
        if (value.isEmpty()) {
            showError(mBinding.passwordLayoutRegis, "Isi Password terlebih dahulu")
            return false
        } else if (value.length < 8) {
            showError(mBinding.passwordLayoutRegis, "Password Minimal 8 Karakter")
            return false
        }
        clearError(mBinding.passwordLayoutRegis)
        return true
    }

//            else if (value.length < 8) {
//                showError(mBinding.ConfirmPasswordTil, "Password konfirmasi Minimal 8 Karakter")
//                return false
//            }

//    private fun validatePassword(): Boolean {
//        val password = mBinding.inputPassword.text.toString()
//        val confirmPassword = mBinding.inputPassword.text.toString()
//        if (password != confirmPassword) {
//            showError(mBinding.passwordLayoutRegis, "Password tidak sesuai")
//            return false
//        }
//        clearError(mBinding.passwordLayoutRegis)
//        return true
//    }

    override fun onClick(view: View?) {
        // Handle button clicks or other UI interactions here
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.inputNamaPengguna -> {
                    if (hasFocus && mBinding.namaPenggunaLayout.isErrorEnabled) {
                        clearError(mBinding.namaPenggunaLayout)
                    } else {
                        validateFullName()
                    }
                }

                R.id.inputEmail -> {
                    if (hasFocus && mBinding.emailLayout.isErrorEnabled) {
                        clearError(mBinding.emailLayout)
                    } else {
                        validateEmail()
                    }
                }

//                R.id.inputPassword -> {
//                    if (hasFocus && mBinding.passwordLayoutRegis.isErrorEnabled) {
//                        clearError(mBinding.passwordLayoutRegis)
//                    } else {
//                        if (validatePassword() && mBinding.passwordLayoutRegis.text!!.isNotEmpty() &&
//                            validateConfirmPassword() && validatePasswordAndConfirmPassword()
//                        ) {
//                            if (mBinding.ConfirmPasswordTil.isErrorEnabled) {
//                                clearError(mBinding.ConfirmPasswordTil)
//                            }
//                            mBinding.ConfirmPasswordTil.apply {
//                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
//                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
//                            }
//                        }
//                    }
//                }
//
//                R.id.ConfirmPasswordEt -> {
//                    if (hasFocus && mBinding.ConfirmPasswordTil.isErrorEnabled) {
//                        clearError(mBinding.ConfirmPasswordTil)
//                    } else {
//                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
//                            if (mBinding.ConfirmPasswordTil.isErrorEnabled) {
//                                clearError(mBinding.ConfirmPasswordTil)
//                            }
//                            mBinding.ConfirmPasswordTil.apply {
//                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
//                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        // Handle key events, e.g., Enter key
        return false
    }

}
