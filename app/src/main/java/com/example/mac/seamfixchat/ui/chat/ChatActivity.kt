package com.example.mac.seamfixchat.ui.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mac.seamfixchat.R
import com.example.mac.seamfixchat.extensions.getAndroidId
import com.example.mac.seamfixchat.helpers.MqttHelper
import com.example.mac.seamfixchat.model.Author
import com.example.mac.seamfixchat.model.Message
import kotlinx.android.synthetic.main.activity_chat.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.joda.time.DateTime




class ChatActivity : AppCompatActivity() {
    private lateinit var mqttHelper: MqttHelper
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        const val VIEW_TYPE_PUB: Int = 0
        const val VIEW_TYPE_SUB: Int = 1
    }

    private val userId by lazy { getAndroidId() }
    //private val userId = "042"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
//Basic Initializations
        initTitle()
        initLayout()
        startMqtt()

        //click listener for FAB
        sendMessage.setOnClickListener {
            publish(messageEdt?.text?.toString())
            messageEdt.text.clear()
        }

    }

    private fun initTitle() {

        title = intent.extras?.getString("title")
    }

    private fun initLayout() {
        messageAdapter = MessageAdapter {
            Toast.makeText(this, "message clicked!", Toast.LENGTH_SHORT)
        }

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        chatRV.layoutManager = linearLayoutManager
        chatRV.adapter = messageAdapter
    }


    private fun startMqtt() {
        //Initialize the MqttHelper
        mqttHelper = MqttHelper(applicationContext, title.toString(), Author(id = userId))
        mqttHelper.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
//                loading.setLoadingText("Connected Successfully...")
//                loading.setLoadingText("Waiting for Messages...")
            }

            override fun connectionLost(throwable: Throwable) {
//                loading.visibility = View.VISIBLE
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", mqttMessage.toString())
                loading.visibility = View.GONE


                val message = Message(ChatActivity.VIEW_TYPE_SUB, DateTime.now().millis, mqttMessage.toString())

                messageAdapter.addItem(message)
                linearLayoutManager.scrollToPosition((messageAdapter.itemCount) - 1)
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

                Log.w("Debug", iMqttDeliveryToken.message.toString())
                loading.visibility = View.GONE

                val message =
                    Message(ChatActivity.VIEW_TYPE_PUB, DateTime.now().millis, iMqttDeliveryToken.message.toString())

                messageAdapter.addItem(message)
                linearLayoutManager.scrollToPosition(messageAdapter.itemCount - 1)
            }
        })
    }

    private fun publish(message: String?) = mqttHelper.publishToTopic(message)

    override fun onDestroy() {
        mqttHelper.mqttAndroidClient.disconnect()
        super.onDestroy()
    }

}
