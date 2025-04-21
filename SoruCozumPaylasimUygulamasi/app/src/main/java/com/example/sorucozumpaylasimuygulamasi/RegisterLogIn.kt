package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sorucozumpaylasimuygulamasi.databinding.RegisterLoginBinding

class RegisterLogIn : AppCompatActivity() {

    private lateinit var binding: RegisterLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterLoginBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGirisYap.setOnClickListener {
            val intent = Intent(this@RegisterLogIn, Login::class.java)
            startActivity(intent)
        }

        binding.btnKayitOl.setOnClickListener {
            val intent = Intent(this@RegisterLogIn, Register::class.java)
            startActivity(intent)
        }

    }
}