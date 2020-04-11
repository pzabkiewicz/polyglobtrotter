package com.example.polyglobtrotter

import android.os.Bundle
import android.text.InputType
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val languageList = createLanguages()
        val adapter = LanguageAdapter(this, languageList)

        val languageFromDropdown = findViewById<AutoCompleteTextView>(R.id.language_from_view)
        languageFromDropdown.inputTurnOff()
        val languageToDropdown = findViewById<AutoCompleteTextView>(R.id.language_to_view)
        languageToDropdown.inputTurnOff()

        languageFromDropdown.setAdapter(adapter)
        onLanguageItemClick(languageFromDropdown, R.id.ic_language_from)
        languageToDropdown.setAdapter(adapter)
        onLanguageItemClick(languageToDropdown, R.id.ic_language_to)
    }

    private fun onLanguageItemClick(languageDropdown: AutoCompleteTextView, flagImageViewId: Int) {
        languageDropdown.setOnItemClickListener { parent, view, position, id ->
            val flagView = findViewById<ImageView>(flagImageViewId)
            val selectedLanguage = parent!!.getItemAtPosition(position) as LanguageDropdownItem
            flagView.setImageResource(selectedLanguage.drawableFlag)
        }
    }

    private fun createLanguages(): List<LanguageDropdownItem>
            = listOf(LanguageDropdownItem(Language.POLISH, R.drawable.ic_polish_flag),
                     LanguageDropdownItem(Language.ENGLISH, R.drawable.ic_english_flag),
                     LanguageDropdownItem(Language.GERMAN, R.drawable.ic_german_flag),
                     LanguageDropdownItem(Language.FRENCH, R.drawable.ic_french_flag),
                     LanguageDropdownItem(Language.ITALIAN, R.drawable.ic_italian_flag),
                     LanguageDropdownItem(Language.SWISS, R.drawable.ic_swiss_flag))

    private fun AutoCompleteTextView.inputTurnOff() {
        this.inputType = InputType.TYPE_NULL
    }
}
