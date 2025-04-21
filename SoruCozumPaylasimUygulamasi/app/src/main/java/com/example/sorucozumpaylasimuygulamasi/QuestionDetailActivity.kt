package com.example.sorucozumpaylasimuygulamasi

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityQuestionDetailBinding

class QuestionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionDetailBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gelen verileri al
        val questionTitle = intent.getStringExtra("QUESTION_TITLE")
        val questionDetail = intent.getStringExtra("QUESTION_DETAIL")
        val imageUrl = intent.getStringExtra("QUESTION_IMAGE") // Fotoğraf URL'si

        // Soru başlığı ve detayını ekrana yazdır
        binding.tvQuestionTitleDetail.text = questionTitle
        binding.tvQuestionDetailDetail.text = questionDetail

        // Glide ile fotoğrafı yükle
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl) // Fotoğraf URL'sini yükle
                .into(binding.ivAnswerImage)
            binding.ivAnswerImage.visibility = View.VISIBLE
        } else {
            binding.ivAnswerImage.visibility = View.GONE
        }

        // Cevap varsa, onu sharedPreferences'tan al
        val sharedPreferences = getSharedPreferences("QuestionAnswers", MODE_PRIVATE)
        val savedAnswer = questionTitle?.let { sharedPreferences.getString(it, null) }

        if (!savedAnswer.isNullOrEmpty()) {
            binding.tvAnswerDetail.text = savedAnswer
            binding.tvAnswerDetail.visibility = View.VISIBLE

            // Fotoğraf URI'sini al ve göster
            val savedImageUriString = sharedPreferences.getString("${questionTitle}_IMAGE_URI", null)
            if (!savedImageUriString.isNullOrEmpty()) {
                val imageUri = Uri.parse(savedImageUriString)
                binding.ivAnswerImage.setImageURI(imageUri)
                binding.ivAnswerImage.visibility = View.VISIBLE
            } else {
                binding.ivAnswerImage.visibility = View.GONE
            }
        } else {
            binding.tvAnswerDetail.visibility = View.GONE
            binding.ivAnswerImage.visibility = View.GONE
        }

        // Cevap butonuna tıklama işlemi
        binding.btnAnswerQuestionDetail.setOnClickListener {
            val intent = Intent(this@QuestionDetailActivity, AnswerQuestionActivity::class.java)
            intent.putExtra("QUESTION_TITLE", questionTitle)
            intent.putExtra("QUESTION_DETAIL", questionDetail)
            startActivity(intent)
        }
    }
}
