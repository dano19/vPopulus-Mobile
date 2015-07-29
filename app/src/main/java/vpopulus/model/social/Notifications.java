package vpopulus.model.social;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vpopulus.model.backend.Cache;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Notifications {

    public int ID;
    public int type;
    public int seen;
    public String message;
    public String value;

    public Notifications(int ID, int type, int seen, String message, String value)
    {
        this.ID = ID;
        this.type = type;
        this.seen = seen;
        this.message = message;
        this.value = value;
    }

    public static List<Notifications> parse(String json)
    {
        List<Notifications> values = new ArrayList<Notifications>();
        try {
            JSONObject object = new JSONObject(json);
            if(object.has("countries")) {
                object = object.getJSONObject("countries");
                if(object.has("country")) {
                    JSONArray array = object.getJSONArray("country");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        values.add(new Notifications(obj.getInt("id"), obj.getInt("type"), obj.getInt("seen"), obj.getString("message"), obj.getString("value")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Cache.notifications.addAll(values);
        return values;

    }

}
