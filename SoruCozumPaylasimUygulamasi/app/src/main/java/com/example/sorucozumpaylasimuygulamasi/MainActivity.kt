package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sorucozumpaylasimuygulamasi.databinding.MainActivityBinding
import com.example.sorucozumpaylasimuygulamasi.roomSoru.AppDatabaseSoru
import com.example.sorucozumpaylasimuygulamasi.roomSoru.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: QuestionAdapter
    private lateinit var database: AppDatabaseSoru

    // AddQuestionActivity'den Gelen Sonucu Yönetmek İçin
    private val addQuestionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("QUESTION_TITLE")
            val category = data?.getStringExtra("QUESTION_CATEGORY")
            val detail = data?.getStringExtra("QUESTION_DETAIL")

            if (title != null && category != null && detail != null) {
                val newQuestion = Questions(
                    title = "$category: $title",
                    detail = detail,
                    category = category
                )

                // Veritabanına soruyu kaydet
                lifecycleScope.launch {
                    // Arka planda veritabanı işlemi
                    withContext(Dispatchers.IO) {
                        database.questionDao().insert(newQuestion)
                    }
                    loadQuestions() // Veritabanını yeniden yükle
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Veritabanını başlat
        database = AppDatabaseSoru.getDatabase(this)

        // RecyclerView Ayarları
        adapter = QuestionAdapter(mutableListOf()) // Boş bir liste ile başlat
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Soru Ekleme Butonu
        binding.fabAddQuestion.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            addQuestionLauncher.launch(intent)
        }

        // Arama Çubuğu için Filtreleme
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterQuestions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Veritabanındaki tüm soruları gözlemle
        database.questionDao().getAllQuestions().observe(this, Observer { questions ->
            // Sorular geldiğinde RecyclerView'ı güncelle
            questions?.let {
                adapter.updateData(it.toMutableList()) // List<Questions> -> MutableList<Questions>
            }
        })

        // popup göster
        binding.settingsImageView.setOnClickListener { showPopupMenu(it) }
    }

    // Filtreleme Fonksiyonu
    private fun filterQuestions(query: String) {
        if (query.isEmpty()){
            loadQuestions()
        } else{
            val filteredList = adapter.getAllQuestions().filter {
                it.title.contains(query, ignoreCase = true) || it.detail.contains(query, ignoreCase = true)
            }
            adapter.updateData(filteredList.toMutableList())
        }
    }

    // Veritabanından soruları al ve RecyclerView'a yükle
    private fun loadQuestions() {
        lifecycleScope.launch {
            // Arka planda veritabanı işlemi
            withContext(Dispatchers.IO) {
                val questions = database.questionDao().getAllQuestions() // LiveData döner
                withContext(Dispatchers.Main) {
                    // RecyclerView'ı güncelle
                    questions.observe(this@MainActivity, Observer { questionList ->
                        questionList?.let {
                            adapter.updateData(it.toMutableList()) // List<Questions> -> MutableList<Questions>
                        }
                    })
                }
            }
        }
    }

    // Popup Menü Gösterimi
    private fun showPopupMenu(anchorView: View) {
        val popupView = LayoutInflater.from(this).inflate(R.layout.settings_panel, null)
        val popupWindow = PopupWindow(
            popupView,
            500, // Genişlik
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT, // Yükseklik
            true // Kapanabilirlik
        )

        popupWindow.showAsDropDown(anchorView, 0, 10)

        // Menüdeki öğeleri tanımla
        popupView.findViewById<View>(R.id.tvProfile).setOnClickListener {
            popupWindow.dismiss() // Popup'ı kapat
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
