package com.example.mac.seamfixchat.general

interface BaseMvpPresenter<T : BaseMvpView> {

    fun attachView(view: T)

    fun destroy()
}