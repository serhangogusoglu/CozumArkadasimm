package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityProfileBinding
import com.example.sorucozumpaylasimuygulamasi.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Room Database başlatılıyor
        userDatabase = AppDatabase.getDatabase(application)

        // Kullanıcı bilgilerini yükle
        loadUserData()

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadUserData() {
        lifecycleScope.launch(Dispatchers.IO) {
            // Kullanıcıyı Room Database üzerinden çek
            val user = userDatabase.userDao().getLastUser() // Son kayıtlı kullanıcıyı al

            withContext(Dispatchers.Main) {
                if (user != null) {
                    binding.tvUsername.setText(user.username)
                    binding.tvEmail.setText(user.email ?: "Email belirtilmedi")
                    binding.tvPassword.setText(user.password)
                } else {
                    Toast.makeText(this@ProfileActivity, "Kullanıcı bilgisi bulunamadı!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
