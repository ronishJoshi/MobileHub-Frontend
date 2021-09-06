package com.example.mobilehubwear

import com.google.android.gms.wearable.WearableListenerService
import com.google.android.gms.wearable.MessageEvent
import android.content.Intent
import android.util.Log

class MyListenerService : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == WEARABLE_DATA_PATH) {
            val message = String(messageEvent.data)
            val startIntent = Intent(this, MainActivity::class.java)
            startIntent.putExtra("message", message)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(startIntent)
            Log.v(TAG, "Main Activity has been started")
        } else {
            super.onMessageReceived(messageEvent)
        }
    }

    companion object {
        const val TAG = "MyDataMAP..."
        const val WEARABLE_DATA_PATH = "/wearable/data/path"
    }
}