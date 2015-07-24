package vpopulus.model.backend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import vpopulus.mobile.AuthActivity;
import vpopulus.mobile.HomeActivity;
import vpopulus.model.entities.Citizen;
import vpopulus.model.politics.Country;

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

    public static void register(Context activity, String username, String password, String password2, String email, String regionName)
    {
        new register(activity, username, password, password2, email, regionName).execute();
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
                Cache.citizen = Citizen.parse(JSONParser.getFromUrl("http://newapi.vpopulus.net/api/citizen/getByToken/" + Cache.token));
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

    public register(Context c, String username, String password, String password2, String email, String regionName)
    {
        this.context = c;
        this.progress = new ProgressDialog(c);
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
        this.regionName = regionName;
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

        String data = JSONParser.getFromUrl("");
        // TODO: Add register feed to API and put link here. http://newapi.vpopulus.net/api/auth/register?username=" + username + "&password=" + password + "

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