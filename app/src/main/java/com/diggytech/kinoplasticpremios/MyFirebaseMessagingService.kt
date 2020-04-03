package com.diggytech.kinoplasticpremios
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
//import android.support.annotation.RequiresApi
//import android.support.v4.app.NotificationCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
//import com.android4dev.pushnotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseToken"
    private lateinit var notificationManager: NotificationManager
    private val ADMIN_CHANNEL_ID = "Android4Dev"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, token)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage?.let { message ->
            //  Log.i(TAG, message.getData().get("message"))

            Log.e(TAG, remoteMessage.data.toString())
//            if (remoteMessage.getData(). > 0) {
//                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//               var  user_id = remoteMessage.getData().get("user_id");
//               var  date = remoteMessage.getData().get("date");
//              var   cal_id = remoteMessage.getData().get("hal_id");
//               var  M_view = remoteMessage.getData().get("M_view");
//            }




            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            /*Bundle[{google.delivered_priority=high,
            google.sent_time=1572437773521, google.ttl=2419200,
             google.original_priority=high, gcm.notification.e=1,
              gcm.notification.alert=1,
              gcm.notification.sound=default,
               gcm.notification.title=Você teve pontos aprovados,
                resgate agora, from=889969894875,
                type=point approved,
                gcm.notification.sound2=default,
                 user_type=notification,
                 google.message_id=0:1572437773527536%a7fcdc93a7fcdc93,
            gcm.notification.body=Você teve pontos aprovados,
             resgate agora,
             google.c.a.e=1,
              collapse_key=com.diggytech.kinoplasticpremios}]*/


            //Setting up Notification channels for android O and above
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                setupNotificationChannels()
            }
            val notificationId = Random().nextInt(60000)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)  //a resource for your custom small icon  //user_id = remoteMessage.getData().get("user_id");
                .setContentTitle(remoteMessage.notification!!.title) //the "title" value you sent in your notification

                .setContentText(remoteMessage.notification!!.body) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build())

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private fun setupNotificationChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }
}