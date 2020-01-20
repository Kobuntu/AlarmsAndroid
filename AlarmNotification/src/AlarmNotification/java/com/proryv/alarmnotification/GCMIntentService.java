package com.proryv.alarmnotification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import static com.proryv.alarmnotification.CommonUtilities.SENDER_ID;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(SENDER_ID);
    }

    public static String REGISTER_ID;

    public static boolean register(Context ctx)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        REGISTER_ID = prefs.getString("regId", "");
        if (REGISTER_ID == null || REGISTER_ID.isEmpty())
        {
            try
            {
                REGISTER_ID = GCMRegistrar.getRegistrationId(ctx);
                if (REGISTER_ID==null || REGISTER_ID.isEmpty()) {
                    GCMRegistrar.register(ctx, SENDER_ID);
                }
            }
            catch (Exception ex)
            {

            }
        }

        return !REGISTER_ID.isEmpty();
    }

    private static final String TAG = "===GCMIntentService===";

    @Override
    protected void onRegistered(Context ctx, String registrationId) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        prefsEditor.putString("regId", registrationId);
        prefsEditor.commit();
        REGISTER_ID = registrationId;
        //Log.i(TAG, "Device registered: regId = " + registrationId);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        //Log.i(TAG, "unregistered = " + arg1);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        if (intent != null)
        {
            try
            {
                String server = intent.getStringExtra("Server");
                String alert = intent.getStringExtra("Alert");
                Integer number = Integer.parseInt(intent.getStringExtra("Number"));
                generateNotification(context, number, server, alert + " " + number);
            }
            catch (Exception ex)
            {

            }
        }
    }

    @Override
    protected void onError(Context arg0, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, Integer number, String title, String desc) {

        Intent notificationIntent = new Intent(context, main_activity.class);
        notificationIntent.putExtra("isFromNotificationBar", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(desc)
                .setNumber(number)
                .setSmallIcon(R.drawable.alarm_brush)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setShowWhen(true)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingIntent)
                .getNotification();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

    }
}
