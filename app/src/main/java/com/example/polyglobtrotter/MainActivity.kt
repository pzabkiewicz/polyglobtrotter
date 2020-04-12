package com.example.polyglobtrotter

import android.os.Bundle
import android.text.InputType
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val DEFAULT_LANGUAGE_FROM = Language.POLISH
    private val DEFAULT_LANGUAGE_TO = Language.ENGLISH
    private val LANGUAGE_TO_DRAWABLE = initMap()

    private lateinit var mLanguageFromDropdown: AutoCompleteTextView;
    private lateinit var mLanguageToDropdown: AutoCompleteTextView;
    private lateinit var mFlagFromImageView: ImageView
    private lateinit var mFlagToImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val languageList = LANGUAGE_TO_DRAWABLE.map { e -> LanguageDropdownItem(e.key, e.value) }
        val adapter = LanguageAdapter(this, languageList)
        mFlagFromImageView = findViewById(R.id.ic_language_from)
        mFlagToImageView = findViewById(R.id.ic_language_to)

        mLanguageFromDropdown.apply {
            inputTurnOff()
            setAdapter(adapter)
            setOnItemClickListener { parent, view, position, id ->
                val selectedLanguage = parent!!.getItemAtPosition(position) as LanguageDropdownItem
                mFlagFromImageView.setImageResource(selectedLanguage.drawableFlag)
            }
        }

        mLanguageToDropdown.apply {
            inputTurnOff()
            setAdapter(adapter)
            setOnItemClickListener { parent, view, position, id ->
                val selectedLanguage = parent!!.getItemAtPosition(position) as LanguageDropdownItem
                mFlagToImageView.setImageResource(selectedLanguage.drawableFlag)
            }
        }

        mFlagFromImageView.setImageResource(LANGUAGE_TO_DRAWABLE[DEFAULT_LANGUAGE_FROM]!!)
        mLanguageFromDropdown.setText(DEFAULT_LANGUAGE_FROM.name, false)
        mFlagToImageView.setImageResource(LANGUAGE_TO_DRAWABLE[DEFAULT_LANGUAGE_TO]!!)
        mLanguageToDropdown.setText(DEFAULT_LANGUAGE_TO.name, false)

        val swapButton = findViewById<Button>(R.id.swap_language_button)
        swapButton.setOnClickListener { swapLanguages() }
    }

    private fun AutoCompleteTextView.inputTurnOff() {
        this.inputType = InputType.TYPE_NULL
    }

    private fun swapLanguages() {
        val oldLangFrom = mLanguageFromDropdown.text.toString()
        val oldLangTo = mLanguageToDropdown.text.toString()
        mLanguageFromDropdown.setText(oldLangTo, false)
        mLanguageToDropdown.setText(oldLangFrom, false)
        mFlagFromImageView.setImageResource(LANGUAGE_TO_DRAWABLE[Language.valueOf(oldLangTo)]!!)
        mFlagToImageView.setImageResource(LANGUAGE_TO_DRAWABLE[Language.valueOf(oldLangFrom)]!!)
    }

    private fun initMap(): EnumMap<Language, Int> {
        val languages = EnumMap<Language, Int>(Language::class.java)
        languages[Language.POLISH] = R.drawable.ic_polish_flag
        languages[Language.ENGLISH] = R.drawable.ic_english_flag
        languages[Language.GERMAN] = R.drawable.ic_german_flag
        languages[Language.FRENCH] = R.drawable.ic_french_flag
        languages[Language.ITALIAN] = R.drawable.ic_italian_flag
        languages[Language.SWISS] = R.drawable.ic_swiss_flag
        return languages
    }

}
