package com.example.mac.seamfixchat.ui.home

import com.example.mac.seamfixchat.general.BaseMvpPresenter
import com.example.mac.seamfixchat.general.BaseMvpView
import com.example.mac.seamfixchat.model.ChatRoom

class HomePresenter<MvpView : BaseMvpView> : BaseMvpPresenter<MvpView> {

    private val defaultChatRooms = ArrayList<ChatRoom>()

    override fun attachView(view: MvpView) {
        this.view = view as HomeView
    }

    override fun destroy() {

    }

    private lateinit var view: HomeView


    fun loadChatRooms(chatRoom: ChatRoom): Unit = view.onLoadChatRooms(chatRoom)

//    fun addChatRoom(chatRoom: ChatRoom) {
//       // defaultChatRooms.add(chatRoom)
////        defaultChatRooms.add(ChatRoom(name = "Mobile"))
//    }

}