package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityRegisterBinding
import com.example.sorucozumpaylasimuygulamasi.room.AppDatabase
import com.example.sorucozumpaylasimuygulamasi.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        binding.btnRegisterKayit.setOnClickListener {
            val username = binding.editTextKullaniciAdi.text.toString()
            val email = binding.editTextEmailAddress.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextPasswordAgain.text.toString()

            // Hata mesajlarını sıfırla
            binding.usernameError.visibility = View.GONE
            binding.emailError.visibility = View.GONE
            binding.passwordError.visibility = View.GONE
            binding.confirmPasswordError.visibility = View.GONE

            var isValid = true

            // Kullanıcı adı doğrulama (en az 1 büyük harf ve 1 sayı içermeli)
            val usernamePattern = Regex("^(?=.*[A-Z])(?=.*\\d).+$")

            if (!usernamePattern.matches(username)) {
                binding.usernameError.text = "En az 1 büyük harf ve 1 sayı içermelidir."
                binding.usernameError.visibility = View.VISIBLE
                isValid = false
            }

            // E-posta doğrulama
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailError.text = "Geçerli bir e-posta adresi girin."
                binding.emailError.visibility = View.VISIBLE
                isValid = false
            }

            // Şifre doğrulama
            if (password.isEmpty()) {
                binding.passwordError.text = "Şifre alanı boş bırakılamaz."
                binding.passwordError.visibility = View.VISIBLE
                isValid = false
            }
            if (confirmPassword.isEmpty()) {
                binding.confirmPasswordError.text = "Şifre tekrar alanı boş bırakılamaz."
                binding.confirmPasswordError.visibility = View.VISIBLE
                isValid = false
            }

            // Şifre eşleşme kontrolü
            if (password != confirmPassword) {
                binding.confirmPasswordError.text = "Şifreler eşleşmiyor."
                binding.confirmPasswordError.visibility = View.VISIBLE
                isValid = false
            }

            // Kayıt işlemi için verileri kaydetme
            if (isValid) {
                // Room Database'e kullanıcıyı ekleme
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = User(username = username, password = password, email = email)
                    userDatabase.userDao().insertUser(user)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Register, "Kayıt başarılı!", Toast.LENGTH_LONG).show()

                        // Kayıt başarılıysa, giriş sayfasına yönlendirme
                        val intent = Intent(this@Register, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                // Hatalı doğrulama durumu
                Toast.makeText(
                    this,
                    "Lütfen tüm alanları doğru şekilde doldurun!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
