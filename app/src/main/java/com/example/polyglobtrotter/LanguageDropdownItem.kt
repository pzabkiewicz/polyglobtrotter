package com.example.polyglobtrotter

data class LanguageDropdownItem(val language: Language, val drawableFlag: Int) {
    override fun toString() = language.name
}