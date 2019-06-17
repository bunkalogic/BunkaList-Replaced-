package com.bunkalogic.bunkalist.Receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bunkalogic.bunkalist.Notification.NotificationHandler
import com.bunkalogic.bunkalist.R

class ReceiverNotification : BroadcastReceiver() {

    var notificationHandler : NotificationHandler? = null

    // is responsible for displaying a notification every 5 days even if the app is not in use
    override fun onReceive(context: Context?, intent: Intent) {
        notificationHandler = NotificationHandler(context!!)

        val nb : Notification.Builder? = notificationHandler!!.createNotificationWeek(
            context.getString(R.string.notification_week_title),
            context.getString(R.string.notification_week_message))
        if (nb != null) {
            notificationHandler?.run { getManager()?.notify(1, nb.build()) }
        }
    }
}