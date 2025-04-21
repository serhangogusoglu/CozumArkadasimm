package com.example.sorucozumpaylasimuygulamasi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityAnswerQuestionBinding

class AnswerQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("QuestionAnswers", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Soru Detaylarından Gelen Veriler
        val questionTitle = intent.getStringExtra("QUESTION_TITLE")
        val questionDetail = intent.getStringExtra("QUESTION_DETAIL")

        binding.tvQuestionTitleQuestion.text = questionTitle
        binding.tvQuestionDetailQuestion.text = questionDetail

        // Cevap kaydetme butonu tıklama
        binding.btnSaveAnswer.setOnClickListener {
            val answer = binding.etAnswerQuestion.text.toString()

            if (answer.isNotEmpty()) {
                // Cevabı sorunun başlığına göre kaydediyoruz
                if (questionTitle != null) {
                    editor.putString(questionTitle, answer)  // Başlıkla cevabı ilişkilendiriyoruz
                    editor.apply()

                    Toast.makeText(this, "Cevap Kaydedildi", Toast.LENGTH_SHORT).show()
                    finish()  // Ekranı kapat
                }
            } else {
                Toast.makeText(this, "Cevap boş olamaz", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
