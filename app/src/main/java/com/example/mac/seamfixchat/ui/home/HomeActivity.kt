package com.example.mac.seamfixchat.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.mac.seamfixchat.R
import com.example.mac.seamfixchat.general.BaseActivity
import com.example.mac.seamfixchat.model.ChatRoom
import com.example.mac.seamfixchat.ui.chat.AddChatRoomDialog
import com.example.mac.seamfixchat.ui.chat.ChatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), HomeView, AddChatRoomDialog.AddChatRoomDialogListener {

    private lateinit var presenter: HomePresenter<HomeView>
    private lateinit var chatRoomAdapter: ChatRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fabAddRoom.setOnClickListener {
            showAddOutreachDialog()
        }

        initLayout()
    }

    private fun initLayout() {
        chatRoomAdapter = ChatRoomAdapter {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("title", it.name)
            startActivity(intent)
        }

        rvChatRooms.adapter = chatRoomAdapter

       // presenter.addChatRoom(ChatRoom(name = "Admin"))
        //presenter.loadChatRooms()
    }

    override fun initPresenter() {
        presenter = HomePresenter()
        presenter.attachView(this)
    }

    override fun onLoadChatRooms(chatRoom: ChatRoom) = chatRoomAdapter.addItem(chatRoom)


    private fun showAddOutreachDialog() {
        val dialog = AddChatRoomDialog.newInstance()
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogStyle)
        dialog.show(supportFragmentManager, "fragment_add_outreach")
    }


    override fun onChatRoomSave(name: String) {
       // presenter.addChatRoom(ChatRoom(name = name))
        presenter.loadChatRooms(ChatRoom(name = name))
    }

}
