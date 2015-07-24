package vpopulus.model.backend;

import java.util.ArrayList;
import java.util.List;

import vpopulus.model.entities.Citizen;
import vpopulus.model.politics.Country;
import vpopulus.model.politics.Region;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Cache {

    public static int userID;
    public static String token;
    public static Citizen citizen;

    public static List<Country> countries = new ArrayList<Country>();
    public static List<Region> regions = new ArrayList<Region>();
}
