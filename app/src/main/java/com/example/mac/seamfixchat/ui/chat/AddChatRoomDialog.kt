package com.example.mac.seamfixchat.ui.chat


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.mac.seamfixchat.R
import kotlinx.android.synthetic.main.fragment_add_chat_room_dialog.*


class AddChatRoomDialog : DialogFragment() {

    private val TAG = "AddChatRoomDialog"

    interface AddChatRoomDialogListener{
        fun onChatRoomSave(name: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // request a window without the title
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_chat_room_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveChatRoomButton.setOnClickListener {
            val listener = activity as AddChatRoomDialogListener
            listener.onChatRoomSave(chatRoomNameEdt?.text.toString())
            dismiss()
        }
    }

    companion object {

        fun newInstance(): AddChatRoomDialog {

            val args = Bundle()

            val fragment = AddChatRoomDialog()
            fragment.arguments = args
            return fragment

        }
    }


}// Required empty public constructor
