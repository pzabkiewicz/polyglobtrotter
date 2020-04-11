package com.example.polyglobtrotter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class LanguageAdapter(val mContext: Context,
                      val mLanguages: List<LanguageDropdownItem>)
    : ArrayAdapter<LanguageDropdownItem>(mContext, 0, mLanguages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dropdownItemView = convertView

        if (dropdownItemView == null)
            dropdownItemView = LayoutInflater.from(mContext)
                                .inflate(R.layout.language_dropdown_item, parent,false)

        val language = mLanguages[position]

        val flagImage: ImageView = dropdownItemView!!.findViewById(R.id.flag_image)
        flagImage.setImageResource(language.drawableFlag)

        val langTextView: TextView = dropdownItemView.findViewById(R.id.language_name)
        langTextView.text = language.language.name

        return dropdownItemView
    }
}

