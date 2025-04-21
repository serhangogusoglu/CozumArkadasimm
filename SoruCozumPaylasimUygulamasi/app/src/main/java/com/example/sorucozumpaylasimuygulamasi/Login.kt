package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityLoginBinding
import com.example.sorucozumpaylasimuygulamasi.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Room Database'i başlat
        userDatabase = AppDatabase.getDatabase(application)

        binding.btnRegisterGiris.setOnClickListener {
            val usernameInput = binding.editTextKullaniciAdi.text.toString()
            val passwordInput = binding.editTextTextPassword.text.toString()

            // Kullanıcıyı veritabanından çek
            lifecycleScope.launch(Dispatchers.IO) {
                val user = userDatabase.userDao().getUser(usernameInput)

                withContext(Dispatchers.Main) {
                    if (user != null && user.password == passwordInput) {
                        // Giriş başarılı
                        Toast.makeText(this@Login, "Giriş başarılı!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Hata mesajı
                        Toast.makeText(this@Login, "Kullanıcı adı veya şifre yanlış!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
