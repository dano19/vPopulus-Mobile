package vpopulus.model.politics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vpopulus.model.backend.Cache;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Region {
    public int ID;
    public String name;
    public int countryID;

    public Region(int ID, String name, int countryID)
    {
        this.ID = ID;
        this.name = name;
        this.countryID = countryID;
    }

    public static List<String> getNames(String countryName)
    {
        List<String> names = new ArrayList<String>();

        int countryID = 0;
        for(int i = 0; i < Cache.countries.size(); i++)
            if(Cache.countries.get(i).name == countryName){
                countryID = Cache.countries.get(i).ID;
                break;
            }

        for(int i = 0; i < Cache.regions.size(); i++)
            if(Cache.regions.get(i).countryID == countryID)
                names.add(Cache.regions.get(i).name);

        return names;
    }

    public static List<Region> parse(String json, int countryID)
    {
        List<Region> regions = new ArrayList<Region>();
        try {
            JSONObject object = new JSONObject(json);
            if(object.has("regions")) {
                object = object.getJSONObject("regions");
                if(object.has("region")) {
                    JSONArray array = object.getJSONArray("region");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject value = array.getJSONObject(i);
                        regions.add(new Region(value.getInt("id"), value.getString("name"), countryID));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return regions;
    }

}
