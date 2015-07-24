package vpopulus.model.economy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vpopulus.model.backend.Cache;
import vpopulus.model.politics.Region;

/**
 * Created by dano19 on 24/07/2015.
 */
public class Inventory {
    public int ID;
    public int itemID;
    public String itemName;
    public int quality;
    public int amount;

    public Inventory(int ID, int itemID, String itemName, int quality, int amount)
    {
        this.ID = ID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.quality = quality;
        this.amount = amount;
    }

    public static List<Inventory> parse(String json)
    {
        List<Inventory> items = new ArrayList<Inventory>();
        try {
            JSONObject object = new JSONObject(json);
            if(object.has("items")) {
                object = object.getJSONObject("items");
                if(object.has("item")) {
                    JSONArray array = object.getJSONArray("item");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject value = array.getJSONObject(i);
                        items.add(new Inventory(value.getInt("id"), value.getInt("item_id"), value.getString("item_name"), value.getInt("quality"), value.getInt("amount")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

}
