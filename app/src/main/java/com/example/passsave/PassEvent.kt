package com.example.passsave

sealed interface PassEvent {
    object Save: PassEvent
    data class SaveTitle(val title: String): PassEvent
    data class SavePass(val pass: String): PassEvent
    object ShowDialog: PassEvent
    object HideDialog: PassEvent
    data class SortPass(val sortType: SortType): PassEvent
    data class DeletePass(val pass: Pass): PassEvent
}
