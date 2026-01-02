package com.firebase.firebasepuishnotification.FcmHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.firebase.firebasepuishnotification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyMessagingService";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            // Handle message within 10 seconds
            handleNow();
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String username = firebaseUser != null ? firebaseUser.getDisplayName() : "User";
            showNotification(remoteMessage.getNotification().getTitle(), "Hey " + username + ", " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    private void showNotification(String title, String message) {
        Log.d(TAG, "Attempting to show notification with title: " + title);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            "FCM_CHANNEL",
                            "Firebase Notifications",
                            NotificationManager.IMPORTANCE_HIGH // CRITICAL CHANGE: Use HIGH Importance
                    );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "FCM_CHANNEL")
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_icon) // Corrected line
                        .setPriority(NotificationCompat.PRIORITY_HIGH) // For older Android versions
                        .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
        Log.d(TAG, "Notification sent to manager.");
    }
}
