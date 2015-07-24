package vpopulus.model.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Citizen {

    public int ID;
    public String name;
    public String avatar;

    public int level;
    public int experience;
    public int experienceRequired;

    public int wellness;

    public Citizen() {
    }

    public Citizen(int ID, String name, String avatar, int level, int wellness, int experience, int experienceRequired)
    {
        this.ID = ID;
        this.name = name;
        this.avatar = avatar;
        this.wellness = wellness;
        this.level = level;
        this.experience = experience;
        this.experienceRequired = experienceRequired;
    }


    public static Citizen parse(String json)
    {
        Citizen citizen = null;

        try {
            JSONObject object = new JSONObject(json);

            if(object.has("citizen"))
            {
                object = object.getJSONObject("citizen");
                citizen = new Citizen();
                citizen.ID = object.getInt("id");
                citizen.name = object.getString("name");
                citizen.avatar = object.getString("avatar");
                citizen.wellness = object.getInt("wellness");
                if(object.has("experience"))
                {
                    JSONObject obj2 = object.getJSONObject("experience");
                    citizen.level = obj2.getInt("level");
                    citizen.experience = obj2.getInt("points");
                    citizen.experienceRequired = obj2.getInt("points_req");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return citizen;
    }

}
