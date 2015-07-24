package vpopulus.model.politics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vpopulus.model.backend.Cache;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Country {

    public int ID;
    public String name;

    public Country(int ID, String name)
    {
        this.ID = ID;
        this.name = name;
    }

    public static void getList()
    {

    }

    public static List<String> getNames()
    {
        List<String> names = new ArrayList<String>();

        for(int i = 0; i < Cache.countries.size(); i++)
            names.add(Cache.countries.get(i).name);

        return names;
    }

    public static List<Country> parse(String json)
    {
        List<Country> countries = new ArrayList<Country>();
        try {
            JSONObject object = new JSONObject(json);
            if(object.has("countries")) {
                object = object.getJSONObject("countries");
                if(object.has("country")) {
                    JSONArray array = object.getJSONArray("country");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject country = array.getJSONObject(i);
                        countries.add(new Country(country.getInt("id"), country.getString("name")));

                        if(country.has("regions"))
                           Cache.regions.addAll(Region.parse(country.toString(), country.getInt("id")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Cache.countries.addAll(countries);
        return countries;

    }

}


