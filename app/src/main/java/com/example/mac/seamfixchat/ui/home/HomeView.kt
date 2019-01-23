package com.example.mac.seamfixchat.ui.home

import com.example.mac.seamfixchat.general.BaseMvpView
import com.example.mac.seamfixchat.model.ChatRoom

interface HomeView : BaseMvpView {

//    fun onLoadChatRooms(chatRooms: List<ChatRoom>)
fun onLoadChatRooms(chatRoom: ChatRoom)
}