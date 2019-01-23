package com.example.mac.seamfixchat.model

import org.joda.time.DateTime

data class Message (
         var id: Int,
         var createdAt: Long = DateTime.now().millis,
         var text: String = ""
)
