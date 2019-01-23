package com.example.mac.seamfixchat.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure.ANDROID_ID
import android.provider.Settings.Secure.getString
import java.util.*


@SuppressLint("HardwareIds")
fun Context.getAndroidId() = getString(this.contentResolver, ANDROID_ID) ?: UUID.randomUUID().toString()
