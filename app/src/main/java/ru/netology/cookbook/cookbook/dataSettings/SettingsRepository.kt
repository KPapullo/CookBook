package ru.netology.cookbook.cookbook.dataSettings


interface SettingsRepository {

    fun saveStateSwitch(key: String, b: Boolean)
    fun getStateSwitch(key: String):Boolean

}