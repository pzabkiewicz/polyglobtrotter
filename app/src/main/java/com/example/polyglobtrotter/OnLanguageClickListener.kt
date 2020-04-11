package com.example.polyglobtrotter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class OnLanguageClickListener(val activity: AppCompatActivity, val mFlagDrawableId: Int) : AdapterView.OnItemClickListener {

    override fun onItemClick(parent: AdapterView<*>?,
                             view: View?,
                             position: Int,
                             id: Long) {

        val flagView = activity.findViewById<ImageView>(mFlagDrawableId)
        val selectedLanguage = parent!!.getItemAtPosition(position) as LanguageDropdownItem
        flagView.setImageResource(selectedLanguage.drawableFlag)

    }
}