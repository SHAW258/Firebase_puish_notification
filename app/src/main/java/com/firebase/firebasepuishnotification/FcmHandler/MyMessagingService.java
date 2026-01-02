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

// This class extends FirebaseMessagingService to handle incoming FCM messages.
public class MyMessagingService extends FirebaseMessagingService {

    // A tag for logging purposes, to easily filter logs for this class.
    private static final String TAG = "MyMessagingService";

    // This method is called when a new FCM token is generated for the device.
    // You would typically send this token to your server to be able to send messages to this specific device.
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    // This method is called when a new FCM message is received.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Log the sender of the message.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if the message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            // This is a placeholder for any short-running task you might want to perform with the data.
            handleNow();
        }

        // Check if the message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            // Get the currently logged-in user from Firebase Authentication.
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            // Get the user's display name if they are logged in, otherwise default to "User".
            String username = firebaseUser != null ? firebaseUser.getDisplayName() : "User";
            // Show a notification with a personalized message.
            showNotification(remoteMessage.getNotification().getTitle(), "Hey " + username + ", " + remoteMessage.getNotification().getBody());
        }
    }

    // A placeholder method for handling any data from the FCM message immediately.
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    // This method creates and displays a notification.
    private void showNotification(String title, String message) {
        Log.d(TAG, "Attempting to show notification with title: " + title);

        // Get the system's NotificationManager service.
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // On Android Oreo (API 26) and above, you must create a notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            "FCM_CHANNEL",
                            "Firebase Notifications",
                            NotificationManager.IMPORTANCE_HIGH // Set the importance level of the notification.
                    );
            manager.createNotificationChannel(channel);
        }

        // Build the notification using NotificationCompat for backward compatibility.
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "FCM_CHANNEL")
                        .setContentTitle(title) // Set the title of the notification.
                        .setContentText(message) // Set the main text of the notification.
                        .setSmallIcon(R.drawable.ic_icon) // Set the small icon for the notification.
                        .setPriority(NotificationCompat.PRIORITY_HIGH) // Set the priority for older Android versions.
                        .setAutoCancel(true); // Automatically dismiss the notification when the user taps it.

        // Display the notification.
        manager.notify((int) System.currentTimeMillis(), builder.build());
        Log.d(TAG, "Notification sent to manager.");
    }
}
