package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sorucozumpaylasimuygulamasi.roomSoru.Questions

class QuestionAdapter(private var questions: MutableList<Questions>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTitle: TextView = itemView.findViewById(R.id.tvQuestionTitle)
       // val questionDetail: TextView = itemView.findViewById(R.id.tvQuestionDetailDetail) // Detay ekleyebilirsiniz.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.questionTitle.text = question.title
       // holder.questionDetail.text = question.detail // Detay alanını da görüntüleyebilirsiniz.

        // 📌 Soruya Tıklama Olayı
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, QuestionDetailActivity::class.java).apply {
                putExtra("QUESTION_TITLE", question.title)
                putExtra("QUESTION_DETAIL", question.detail)
                putExtra("QUESTION_CATEGORY", question.category) // Kategoriyi de ekleyebilirsiniz.
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = questions.size

    // 📢 Listeyi Güncelleme Fonksiyonu
    fun updateData(newQuestions: MutableList<Questions>) {
        questions = newQuestions
        notifyDataSetChanged()
    }


    fun getAllQuestions(): List<Questions> {
        return questions
    }

}
