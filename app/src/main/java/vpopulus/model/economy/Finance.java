package vpopulus.model.economy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dano19 on 24/07/2015.
 */
public class Finance {
    public int ID;
    public int currencyID;
    public String currencyName;
    public Double amount;

    public Finance(int ID, int currencyID, String currencyName, Double amount)
    {
        this.ID = ID;
        this.currencyID = currencyID;
        this.currencyName = currencyName;
        this.amount = amount;
    }

    public static List<Finance> parse(String json)
    {
        List<Finance> items = new ArrayList<Finance>();
        try {
            JSONObject object = new JSONObject(json);
            if(object.has("finances")) {
                object = object.getJSONObject("finances");
                if(object.has("finance")) {
                    JSONArray array = object.getJSONArray("finance");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject value = array.getJSONObject(i);
                        items.add(new Finance(value.getInt("id"), value.getInt("currency_id"), value.getString("currency_name"), value.getDouble("amount")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

}
