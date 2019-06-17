package com.bunkalogic.bunkalist.Notification

import android.content.ContextWrapper
import android.os.Build
import android.R.attr.colorPrimary
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.graphics.drawable.Icon
import com.bunkalogic.bunkalist.Activities.BaseActivity
import com.bunkalogic.bunkalist.Activities.OtherActivities.MainEmptyActivity
import com.bunkalogic.bunkalist.R
import android.app.AlarmManager
import com.bunkalogic.bunkalist.Activities.SettingsActivities.EditProfileActivity
import java.util.*




class NotificationHandler(context: Context) : ContextWrapper(context) {

    private var manager: NotificationManager? = null
    private val CHANNEL_HIGH_NAME = "HIGH CHANNEL"
    private val CHANNEL_DEFAULT_NAME = "DEFAULT CHANNEL"
    private val CHANNEL_LOW_NAME = "LOW CHANNEL"
    private val SUMMARY_GROUP_ID = 1001
    private val SUMMARY_GROUP_NAME = "GROUPING_NOTIFICATION"

    init {
        createChannels()
    }

    fun getManager(): NotificationManager? {
        if (manager == null) {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return manager
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            // Creating High Channel
            val highChannel = NotificationChannel(
                CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH
            )

            // ...Extra Config...
            highChannel.enableLights(true)
            highChannel.lightColor = Color.MAGENTA
            highChannel.setShowBadge(true)
            highChannel.enableVibration(true)
            // highChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // highChannel.setSound(defaultSoundUri, null);

            highChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            val defaultChannel = NotificationChannel(
                CHANNEL_DEFAULT_ID, CHANNEL_DEFAULT_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            defaultChannel.enableLights(true)
            defaultChannel.lightColor = Color.MAGENTA
            defaultChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE



            val lowChannel = NotificationChannel(
                CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW
            )
            lowChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET

            getManager()!!.createNotificationChannel(highChannel)
            getManager()!!.createNotificationChannel(lowChannel)
            getManager()!!.createNotificationChannel(defaultChannel)
        }
    }

    fun createNotification(title: String, message: String, isHighImportance: Boolean): Notification.Builder? {
        return if (Build.VERSION.SDK_INT >= 26) {
            if (isHighImportance) {
                this.createNotificationWithChannel(title, message, CHANNEL_HIGH_ID)
            } else this.createNotificationWithChannel(title, message, CHANNEL_LOW_ID)
        } else this.createNotificationWithChannel(title, message, CHANNEL_DEFAULT_ID)
    }

    fun createNotificationWeek(title: String, message: String): Notification.Builder? {
        return if (Build.VERSION.SDK_INT >= 26) {
            this.createNotificationWeekWithChannel(title, message, CHANNEL_DEFAULT_ID)
        } else this.createNotificationWithChannel(title, message, CHANNEL_DEFAULT_ID)
    }

    private fun createNotificationWithChannel(
        title: String,
        message: String,
        channelId: String
    ): Notification.Builder? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, BaseActivity::class.java)
            //intent.putExtra("title", title)
            //intent.putExtra("message", message)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)


            return Notification.Builder(applicationContext, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pIntent)
                .setColor(getColor(R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setGroup(SUMMARY_GROUP_NAME)
                .setAutoCancel(true)
        }
        return null
    }

    private fun createNotificationWeekWithChannel(
        title: String,
        message: String,
        channelId: String
    ): Notification.Builder? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, MainEmptyActivity::class.java)
            //intent.putExtra("title", title)
            //intent.putExtra("message", message)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            //val alarmManager1 = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            //val calendar1Notify = Calendar.getInstance()
            //calendar1Notify.timeInMillis = System.currentTimeMillis()
            //calendar1Notify.set(Calendar.DAY_OF_WEEK, 4)
            //calendar1Notify.set(Calendar.HOUR_OF_DAY, 6)
            //calendar1Notify.set(Calendar.MINUTE, 39)
//
            //alarmManager1.set(AlarmManager.RTC_WAKEUP, calendar1Notify.timeInMillis, pIntentService)
            //val timeWeek = (7 * 24 * 60 * 60 * 1000).toLong()
            //alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar1Notify.timeInMillis, timeWeek, pIntentService)


            return Notification.Builder(applicationContext, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pIntent)
                .setColor(getColor(R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_stat_letters_notfications)
                .setGroup(SUMMARY_GROUP_NAME)
                .setAutoCancel(true)
        }
        return null
    }





    fun publishNotificationSummaryGroup(isHighImportance: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = if (isHighImportance) CHANNEL_HIGH_ID else CHANNEL_LOW_ID
            val summaryNotification = Notification.Builder(applicationContext, channelId)
                .setSmallIcon(android.R.drawable.stat_notify_call_mute)
                .setGroup(SUMMARY_GROUP_NAME)
                .setGroupSummary(true)
                .build()
            getManager()!!.notify(SUMMARY_GROUP_ID, summaryNotification)
        }
    }

    companion object {

        val CHANNEL_HIGH_ID = "1"
        val CHANNEL_LOW_ID = "2"
        val CHANNEL_DEFAULT_ID = "3"
    }
}