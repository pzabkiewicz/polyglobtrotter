package com.example.polyglobtrotter

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mLanguageFromDropdown: AutoCompleteTextView;
    private lateinit var mFlagFromImageView: ImageView
    private lateinit var mLanguagesFilter: ChipGroup

    private lateinit var mWordsRecyclerView: RecyclerView
    private lateinit var mWordsRecycleViewManager: RecyclerView.LayoutManager
    private lateinit var mWordsRecyclerViewAdapter: RecyclerView.Adapter<*>

    private lateinit var mWordList: Array<String>

    private var mCurrentLanguageChoice: Language = DEFAULT_LANGUAGE_FROM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val languageList = LANGUAGE_TO_DRAWABLE.map { e -> LanguageDropdownItem(e.key, e.value) }

        // Dropdown
        val adapter = LanguageAdapter(this, languageList)
        mFlagFromImageView = imgLanguageFrom
        mLanguageFromDropdown = languageFromView

        setupLanguageDropdownView(mLanguageFromDropdown, adapter, mFlagFromImageView)

        mLanguagesFilter = languagesFilter
        LANGUAGE_TO_DRAWABLE.entries
            .filter { it.key != mCurrentLanguageChoice }
            .forEach { addLanguageChip(it.toPair()) }

        val languageFrom: Language = Language.valueOf(mLanguageFromDropdown.text.toString())
        mWordList = WORDS?.get(languageFrom) ?: arrayOf()

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

                if (selectedLanguage.language == mCurrentLanguageChoice)
                    return@setOnItemClickListener

                flagView.setImageResource(selectedLanguage.drawableFlag)
                val chipToRemove: View? = mLanguagesFilter.children
                    .find { (it as Chip).text.toString() == selectedLanguage.language.name }

                chipToRemove?.apply {
                    mLanguagesFilter.removeView(this)
                    val languageImg = LANGUAGE_TO_DRAWABLE[mCurrentLanguageChoice]!!
                    addLanguageChip(mCurrentLanguageChoice to languageImg)
                    mCurrentLanguageChoice = selectedLanguage.language
                }

            }
        }

        mFlagFromImageView.setImageResource(LANGUAGE_TO_DRAWABLE[DEFAULT_LANGUAGE_FROM]!!)
        mLanguageFromDropdown.setText(DEFAULT_LANGUAGE_FROM.name, false)
    }

    private fun addLanguageChip(language: Pair<Language, Int>) {
        val chip = Chip(mLanguagesFilter.context)
        chip.text = language.first.name
        chip.isSelected = true
        chip.isCheckable = true
        chip.chipIcon = getDrawable(language.second)
        mLanguagesFilter.addView(chip)
    }

    private fun AutoCompleteTextView.inputTurnOff() {
        this.inputType = InputType.TYPE_NULL
    }

    companion object {
        private val DEFAULT_LANGUAGE_FROM = Language.POLISH
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
