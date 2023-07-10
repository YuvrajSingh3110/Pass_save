package com.example.passsave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class PassViewModel(
    private val dao: PassDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.Title)
    private val _pass = _sortType
        .flatMapLatest {_sortType->
            when(_sortType){
                SortType.Title -> dao.orderByTitle()
                SortType.Pass -> dao.orderByPass()
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
        )
    private val _state = MutableStateFlow(PassState())
    //combining different flows into state
    val state = combine(_state, _sortType, _pass) {state, sortType, pass ->
        state.copy(
            pass = pass,
            sortType = sortType
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), PassState()
    )

    fun  onEvent(event: PassEvent){
        when(event){
            is PassEvent.DeletePass -> {
                viewModelScope.launch {
                    dao.deletePass(event.pass)
                }
            }
            PassEvent.HideDialog -> {
                _state.update {it.copy(
                 isAddingContact = false
                )}
            }
            PassEvent.Save -> {
                val title = state.value.title
                val password = state.value.password

                if (title.isBlank() || password.isBlank()){
                    return
                }
                val pass = Pass(
                    title = title,
                    pass = password
                )
                viewModelScope.launch {
                    dao.upsertPass(pass)
                }
                _state.update {it.copy(
                    isAddingContact = false,
                    title = "",
                    password = "",
                )}
            }
            is PassEvent.SavePass -> {
                _state.update { it.copy(
                    pass = it.pass
                ) }
            }
            is PassEvent.SaveTitle -> {
                _state.update { it.copy(
                    title = it.title
                ) }
            }
            PassEvent.ShowDialog -> {
                _state.update {it.copy(
                    isAddingContact = true
                )}
            }
            is PassEvent.SortPass -> {
                _sortType.value = event.sortType
            }
        }
    }
}