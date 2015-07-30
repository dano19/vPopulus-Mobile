package vpopulus.model.backend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import vpopulus.Other.DisplayNotificationsService;
import vpopulus.mobile.AuthActivity;
import vpopulus.mobile.HomeActivity;
import vpopulus.mobile.R;
import vpopulus.model.economy.Inventory;
import vpopulus.model.entities.Citizen;
import vpopulus.model.politics.Country;
import vpopulus.model.social.Notifications;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Auth {

    public static void preloadData(AuthActivity activity)
    {
        if(Cache.countries.size() <= 0)
            new preloadData(activity).execute();
    }

    public static void authenticate(Context activity, String username, String password)
    {
        new login(activity, username, password).execute();
    }

    public static void login (String username, String password, DisplayNotificationsService act, String notService)
    {
        new login2(username, password, act, notService).execute();
    }

    public static void register(Context activity, String username, String password, String password2, String email, String regionName, char gender)
    {
        new register(activity, username, password, password2, email, regionName, gender).execute();
    }
}

class preloadData extends AsyncTask<String, Integer, String>
{
    private AuthActivity context;
    private ProgressDialog progress;

    public preloadData(AuthActivity c)
    {
        context = c;
        progress = new ProgressDialog(c);
    }

    @Override
    protected void onPreExecute()
    {
        progress.setMessage("Getting data...");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected String doInBackground(String... arg0) {

        int i = 1;
        while(true) {
            String data = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/country/getlist/" + i);
            i += 1;
            if(Country.parse(data).size() == 10)
                continue;
            break;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result)
    {
        context.startActivity(new Intent(context, AuthActivity.class));
        progress.dismiss();
    }
}

class login2 extends AsyncTask<String, Integer, Boolean>
{
    private DisplayNotificationsService act;
    private String notService;
    private String username;
    private String password;

    public login2(String username, String password, DisplayNotificationsService act, String notService)
    {
        this.act = act;
        this.notService = notService;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        String data = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/auth/login/" + username + "/" + password);

        try {
            JSONObject object = new JSONObject(data);

            if(object.has("result"))
            {
                object = object.getJSONObject("result");
                Cache.userID = object.getInt("user_id");
                Cache.token = object.getString("token");
                String json = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/citizen/getByToken/" + Cache.token);
                Cache.unreadNotifications = Notifications.countUnread(json);
                return true;
            } else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(Cache.unreadNotifications > 0) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(""));

            PendingIntent pendingIntent = PendingIntent.getActivity(act, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(act);

            builder.setSmallIcon(R.drawable.ic_drawer);

            builder.setContentIntent(pendingIntent);

            builder.setAutoCancel(true);

            builder.setLargeIcon(BitmapFactory.decodeResource(act.getResources(), R.drawable.icon_128));

            builder.setContentTitle("vPopulus Notifications");
            builder.setSubText("Tap to see them.");

            if (result == true) {
                builder.setContentText("You got " + Cache.unreadNotifications + " unread notifications!");

            } else {
                builder.setContentText("Something went wrong!");
            }

            NotificationManager notificationManager = (NotificationManager) act.getSystemService(
                    notService);
            notificationManager.notify(0, builder.build());
        }
    }
}

class login extends AsyncTask<String, Integer, Boolean>
{
    private Context context;
    private ProgressDialog progress;
    private String username;
    private String password;

    public login(Context c, String username, String password)
    {
        this.context = c;
        this.progress = new ProgressDialog(c);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute()
    {
        progress.setMessage("Authentication in progress..");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        String data = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/auth/login/" + username + "/" + password);

        try {
            JSONObject object = new JSONObject(data);

            if(object.has("result"))
            {
                object = object.getJSONObject("result");
                Cache.userID = object.getInt("user_id");
                Cache.token = object.getString("token");
                String json = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/citizen/getByToken/" + Cache.token);
                Cache.citizen = Citizen.parse(json);
                Cache.notifications = Notifications.parse(json);
                Cache.inventory = Inventory.parse(json);
                return true;
            } else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(result == true)
            context.startActivity(new Intent(context, HomeActivity.class));
        else
        {
            new AlertDialog.Builder(context)
                    .setMessage("Wrong username or password.")
                    .show();
            // TODO: Add more error messages.
        }
        progress.dismiss();
    }
}

class register extends AsyncTask<String, Integer, Boolean>
{
    private Context context;
    private ProgressDialog progress;
    private String username;
    private String password;
    private String password2;
    private String email;
    private String regionName;
    private char gender;

    public register(Context c, String username, String password, String password2, String email, String regionName, char gender)
    {
        this.context = c;
        this.progress = new ProgressDialog(c);
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
        this.regionName = regionName;
        this.gender = gender;
    }

    @Override
    protected void onPreExecute()
    {
        progress.setMessage("Registering in progress..");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        int regionID = 0;
        for(int i = 0; i < Cache.regions.size(); i++)
            if(Cache.regions.get(i).name == regionName)
            {
                regionID = Cache.regions.get(i).ID;
                break;
            }

        String data = JSONParser.getFromUrl("http://newapi.vpopulus.net/api/auth/register?username=" + username + "&password=" + password + "&password2=" + password2 + "&email=" + email + "&regionID=" + regionID + "&gender=" + gender);

        try {
            JSONObject object = new JSONObject(data);

            if(object.has("result"))
            {
                return true;
            } else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(result == true) {
            context.startActivity(new Intent(context, AuthActivity.class));
            new AlertDialog.Builder(context)
                    .setMessage("Account has been registered! Please check your email for activation link.")
                    .show();
        } else
        {
            new AlertDialog.Builder(context)
                    .setMessage("Missing fields.")
                    .show();
            // TODO: Add more error messages.
        }
        progress.dismiss();
    }
}