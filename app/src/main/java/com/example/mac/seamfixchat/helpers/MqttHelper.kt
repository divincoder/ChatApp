package com.example.mac.seamfixchat.helpers

import android.content.Context
import android.util.Log
import com.example.mac.seamfixchat.model.Author
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttHelper(
    context: Context,
    private val topic: String,
    private val author: Author
) {

    var mqttAndroidClient: MqttAndroidClient

    companion object {
        private const val QOS_LEVEL = 1
        private const val ACCOUNT_USERNAME = "boipomxz"
        private const val ACCOUNT_PASSWORD = "Ss0wXGXy-Q5-"
        private const val serverUri = "tcp://m16.cloudmqtt.com:19843"
    }

    init {

        mqttAndroidClient = MqttAndroidClient(context, serverUri, author.id)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.w("mqtt", s)
            }

            override fun connectionLost(throwable: Throwable) {

            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Mqtt", mqttMessage.toString())
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

            }
        })
        connect()
    }

    fun setCallback(callback: MqttCallbackExtended) {
        mqttAndroidClient.setCallback(callback)
    }

    private fun connect() {

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.userName = ACCOUNT_USERNAME
        mqttConnectOptions.password = ACCOUNT_PASSWORD.toCharArray()

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {

                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = true
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString())
                }
            })


        } catch (ex: MqttException) {
            ex.printStackTrace()
        }

    }


    private fun subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(topic, QOS_LEVEL, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.w("Mqtt", "Subscribed!")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.w("Mqtt", "Subscribed fail!")
                }
            })

        } catch (ex: MqttException) {
            System.err.println("Exceptions subscribing")
            ex.printStackTrace()
        }
    }

    fun publishToTopic(message: String?) {
        val mqttMessage = MqttMessage(message?.toByteArray()).apply {
            isRetained = true
            //id = author.id.toInt()
        }

        try {
            mqttAndroidClient.publish(topic, mqttMessage)

        } catch (ex: MqttException) {
            System.err.println("Exception publishing")
            ex.printStackTrace()
        }

        //mqttAndroidClient.disconnect()
    }

    private val onConnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken) {

            val message = MqttMessage("Hello World".toByteArray())
            try {
                mqttAndroidClient.publish("test", message)
            } catch (e: MqttException) {
                e.printStackTrace()
            }

        }

        override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {}
    }

    fun publish(message: String?) = mqttAndroidClient.publish(topic, message?.toByteArray(), QOS_LEVEL, false)!!
//
//    private fun subscribeOnTopic() = mqttClient.subscribe(topic, QOS_LEVEL, actionOnSubscribed)
}