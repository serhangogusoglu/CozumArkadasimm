package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sorucozumpaylasimuygulamasi.databinding.ActivityAddquestionBinding

class AddQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddquestionBinding
    private val IMAGE_PICK_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddquestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kategori Seçimi için Spinner Ayarı
        val categories = arrayOf("Matematik", "Fizik", "Kimya", "Biyoloji", "Tarih", "Coğrafya", "Edebiyat", "Yabancı Dil")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        // Kaydet Butonu
        binding.btnSaveQuestion.setOnClickListener {
            val title = binding.etQuestionTitle.text.toString()
            val detail = binding.etQuestionDetail.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()

            if (title.isEmpty() || detail.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_LONG).show()
            } else {
                // **MainActivity'ye veriyi geri göndermek için Intent kullanıyoruz**
                val resultIntent = Intent()
                resultIntent.putExtra("QUESTION_TITLE", title)
                resultIntent.putExtra("QUESTION_DETAIL", detail)
                resultIntent.putExtra("QUESTION_CATEGORY", category)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // Fotoğraf Yükleme Butonu
        binding.btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                binding.imageView.setImageURI(imageUri)
                Toast.makeText(this, "Fotoğraf yüklendi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
