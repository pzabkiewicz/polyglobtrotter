package com.example.polyglobtrotter

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mLanguageFromDropdown: AutoCompleteTextView;
    private lateinit var mLanguageToDropdown: AutoCompleteTextView;
    private lateinit var mFlagFromImageView: ImageView
    private lateinit var mFlagToImageView: ImageView

    private lateinit var mWordsRecyclerView: RecyclerView
    private lateinit var mWordsRecycleViewManager: RecyclerView.LayoutManager
    private lateinit var mWordsRecyclerViewAdapter: RecyclerView.Adapter<*>

    private lateinit var mWordList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val languageList = LANGUAGE_TO_DRAWABLE.map { e -> LanguageDropdownItem(e.key, e.value) }
        val adapter = LanguageAdapter(this, languageList)
        mFlagFromImageView = imgLanguageFrom
        mFlagToImageView = imgLanguageTo
        mLanguageFromDropdown = languageFromView
        mLanguageToDropdown = language_to_view

        setupLanguageDropdownView(mLanguageFromDropdown, adapter, mFlagFromImageView)
        setupLanguageDropdownView(mLanguageToDropdown, adapter, mFlagToImageView)

        mFlagFromImageView.setImageResource(LANGUAGE_TO_DRAWABLE[DEFAULT_LANGUAGE_FROM]!!)
        mLanguageFromDropdown.setText(DEFAULT_LANGUAGE_FROM.name, false)
        mFlagToImageView.setImageResource(LANGUAGE_TO_DRAWABLE[DEFAULT_LANGUAGE_TO]!!)
        mLanguageToDropdown.setText(DEFAULT_LANGUAGE_TO.name, false)

        val languageFrom: Language = Language.valueOf(mLanguageFromDropdown.text.toString())
        mWordList = WORDS?.get(languageFrom) ?: arrayOf()


        val swapButton = swapLanguageButton
        swapButton.setOnClickListener { onSwapLanguages() }

        mWordsRecycleViewManager = LinearLayoutManager(this)
        mWordsRecyclerViewAdapter = WordRecyclerViewAdapter(mWordList)
        mWordsRecyclerView = wordsRecyclerView
        mWordsRecyclerView.apply {
            layoutManager = mWordsRecycleViewManager
            setAdapter(mWordsRecyclerViewAdapter)
        }
    }

    private fun setupLanguageDropdownView(languageDropdownView: AutoCompleteTextView,
                                          adapter: LanguageAdapter,
                                          flagView: ImageView) {

        languageDropdownView.apply {
            inputTurnOff()
            setAdapter(adapter)
            setOnItemClickListener { parent, view, position, id ->
                val selectedLanguage = parent!!.getItemAtPosition(position) as LanguageDropdownItem
                flagView.setImageResource(selectedLanguage.drawableFlag)
            }
        }
    }

    private fun AutoCompleteTextView.inputTurnOff() {
        this.inputType = InputType.TYPE_NULL
    }

    private fun onSwapLanguages() {
        val oldLangFrom = mLanguageFromDropdown.text.toString()
        val oldLangTo = mLanguageToDropdown.text.toString()
        mLanguageFromDropdown.setText(oldLangTo, false)
        mLanguageToDropdown.setText(oldLangFrom, false)

        val newLanguageFrom = Language.valueOf(oldLangTo)

        mFlagFromImageView.setImageResource(LANGUAGE_TO_DRAWABLE[newLanguageFrom]!!)
        mFlagToImageView.setImageResource(LANGUAGE_TO_DRAWABLE[Language.valueOf(oldLangFrom)]!!)

        mWordList = WORDS?.get(newLanguageFrom) ?: arrayOf()
    }

    companion object {
        private val DEFAULT_LANGUAGE_FROM = Language.POLISH
        private val DEFAULT_LANGUAGE_TO = Language.ENGLISH
        private val LANGUAGE_TO_DRAWABLE = initMap()

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

}
