package com.sanathcoding.noteapplication.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class ByTitle(orderType: OrderType): NoteOrder(orderType)
    class ByDate(orderType: OrderType): NoteOrder(orderType)
    class ByColor(orderType: OrderType): NoteOrder(orderType)
}
