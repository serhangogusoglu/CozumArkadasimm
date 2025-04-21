package com.example.sorucozumpaylasimuygulamasi

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class SettingsDialogFragment : DialogFragment() {

    private var startY = 0 // Y konumu için değişken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_panel, container, false)

        //"Profilim" TextView'ine tıklama
        val tvProfile = view.findViewById<TextView>(R.id.tvProfile)
        tvProfile.setOnClickListener {
            // ProfileActivity'yi başlat
            val intent = Intent(requireContext(), ProfileActivity::class.java)

            // Örnek kullanıcı bilgilerini gönderiyoruz (bunları dinamik hale getirebilirsin)
            intent.putExtra("USER_NAME", "Ahmet Yılmaz")
            intent.putExtra("USER_EMAIL", "ahmet@example.com")
            intent.putExtra("USER_PHONE", "0555 123 45 67")

            startActivity(intent)
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(350, WindowManager.LayoutParams.WRAP_CONTENT) // Genişlik sabit, yükseklik içerik kadar
            setGravity(Gravity.TOP or Gravity.END) // Sağ üst köşeye hizalar
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // Panelin yukarıdan ne kadar mesafede başlayacağını ayarlamak için
            val params = attributes
            params.y = startY // Butonun altından başlat
            attributes = params
        }
    }

    fun setStartY(y: Int) {
        startY = y
    }
}
