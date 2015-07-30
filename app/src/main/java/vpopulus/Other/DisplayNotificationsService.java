package vpopulus.Other;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import vpopulus.mobile.R;
import vpopulus.model.backend.Auth;
import vpopulus.model.backend.Cache;

public class DisplayNotificationsService extends Service {

    @Override
    public int onStartCommand(Intent pintent, int flags, int startId) {
        Auth.login("admin", "testing12345", this, NOTIFICATION_SERVICE);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}


/*public class DisplayNotificationsService extends IntentService {

public DisplayNotificationsService() {
    super("");

}

    public DisplayNotificationsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        runService();

    }

    Date lastCheck;

    protected void runService() {
        lastCheck = new Date();
        while(true)
        {
            Date dateNow = new Date();
            long diff = dateNow.getTime() - lastCheck.getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            if(Cache.token != null && diffMinutes >= 1) {
                lastCheck = dateNow;
                String json = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/citizen/getByToken/" + Cache.token);
                Cache.notifications = Notifications.parse(json);
                if(Cache.notifications.size() > 0)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                    builder.setSmallIcon(R.drawable.ic_drawer);

                    builder.setContentIntent(pendingIntent);

                    builder.setAutoCancel(true);

                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_128));

                    builder.setContentTitle("vPopulus Notifications");
                    builder.setContentText("You got " + Cache.notifications.size() + " unread notifications!");
                    builder.setSubText("Tap to see them.");

                    NotificationManager notificationManager = (NotificationManager) this.getSystemService(
                            NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
                    // END_INCLUDE(send_notification)
                }
            }
        }
    }
}*/