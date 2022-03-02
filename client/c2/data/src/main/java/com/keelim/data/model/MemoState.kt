package com.keelim.data.model

import com.keelim.data.model.fold.Memo

sealed class MemoState {
    object UnInitialized : MemoState()
    object Loading : MemoState()
    data class Error(val message: String) : MemoState()
    data class Success(val data: List<Memo>) : MemoState()
}