package com.example.mac.seamfixchat.ui.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mac.seamfixchat.R
import com.example.mac.seamfixchat.model.Message
import kotlinx.android.synthetic.main.rc_item_message_friend.view.*
import kotlinx.android.synthetic.main.rc_item_message_user.view.*
import java.util.*

internal class MessageAdapter (val action: (message: Message) -> Unit) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = ArrayList<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == ChatActivity.VIEW_TYPE_PUB) {
            ItemMessageUserViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rc_item_message_user,
                    parent,
                    false
                )
            )
        } else
            ItemMessageFriendViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rc_item_message_friend,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemMessageFriendViewHolder)
            holder.draw(messages[position])
        else if (holder is ItemMessageUserViewHolder)
            holder.draw(messages[position])
    }

    override fun getItemCount() = messages.size

    fun addItems(items: List<Message>) {
        messages.clear()
        messages.addAll(items)
        notifyDataSetChanged()
    }

    fun update(message: Message) {
        var position = -1
        messages.indices.forEach {
            if (message.id == messages[it].id) {
                messages[it].text = message.text
                position = it
                return
            }
        }
        notifyItemChanged(position)
    }

    fun addItem(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
        notifyDataSetChanged()
    }

    fun clear() {
        messages.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].id == ChatActivity.VIEW_TYPE_PUB)
            ChatActivity.VIEW_TYPE_PUB
        else
            ChatActivity.VIEW_TYPE_SUB
    }


    internal inner class ItemMessageUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun draw(message: Message) {
            with(itemView) {
                textContentUser.text = message.text

                setOnClickListener { action.invoke(message) }
            }
        }
    }

    internal inner class ItemMessageFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun draw(message: Message) {
            with(itemView) {
                textContentFriend.text = message.text

                setOnClickListener { action.invoke(message) }
            }
        }
    }
}