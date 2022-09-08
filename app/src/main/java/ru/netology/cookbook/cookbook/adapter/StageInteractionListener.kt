package ru.netology.cookbook.cookbook.adapter

import ru.netology.cookbook.cookbook.classes.Stage

interface StageInteractionListener {

    fun onRemoveStageClicked(stage: Stage)
    fun onSaveStageClicked(content: String, uriPhoto: String?)
    fun onMoveStageUpClicked(position: Int)
    fun onMoveStageDownClicked(position: Int)
}