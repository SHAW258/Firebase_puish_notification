package com.firebase.firebasepuishnotification.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firebase.firebasepuishnotification.R;
import com.google.firebase.messaging.FirebaseMessaging;

// This is the main activity of the application, which is the first screen the user sees.
public class MainActivity extends AppCompatActivity {

    // A tag for logging purposes, to easily filter logs for this class.
    private static final String TAG = "MainActivity";

    // This method is called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This enables edge-to-edge display, allowing the app to draw under the system bars.
        EdgeToEdge.enable(this);
        // This sets the layout for this activity, which is defined in the activity_main.xml file.
        setContentView(R.layout.activity_main);

        // This listener is used to handle window insets, which are the parts of the screen obscured by system UI (like the status bar and navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    // This sets the padding of the main view to account for the system bars, preventing the app's content from being hidden behind them.
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                });

        // Request permission to post notifications (required for Android 13 and above).
        requestNotificationPermission();
        // Get the FCM registration token for this device.
        getFcmToken();
        // Subscribe this device to the "all" topic, so it can receive messages sent to this topic.
        subscribeToTopic();
    }

    // This method subscribes the device to the "all" FCM topic.
    private void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed to 'all' topic";
                    if (!task.isSuccessful()) {
                        msg = "Subscription to 'all' topic failed";
                    }
                    Log.d(TAG, msg);
                });
    }

    // This method retrieves the FCM registration token for the device.
    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the new FCM registration token.
                    String token = task.getResult();

                    // Log the token.
                    Log.d(TAG, "FCM Token: " + token);
                });
    }

    // This method requests the POST_NOTIFICATIONS permission from the user if it has not already been granted.
    private void requestNotificationPermission() {
        // This permission is only required on Android 13 (API 33) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Request the permission from the user.
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1
                );
            }
        }
    }
}
