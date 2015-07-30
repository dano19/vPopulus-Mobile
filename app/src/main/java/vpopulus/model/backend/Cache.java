package vpopulus.model.backend;

import java.util.ArrayList;
import java.util.List;

import vpopulus.model.economy.Inventory;
import vpopulus.model.entities.Citizen;
import vpopulus.model.politics.Country;
import vpopulus.model.politics.Region;
import vpopulus.model.social.Notifications;

/**
 * Created by Dano1_000 on 7/24/2015.
 */
public class Cache {

    public static int userID;
    public static String token;
    public static Citizen citizen;

    public static int unreadNotifications = 0;
    public static int currentPage = 1;

    public static List<Country> countries = new ArrayList<Country>();
    public static List<Region> regions = new ArrayList<Region>();
    public static List<Notifications> notifications = new ArrayList<Notifications>();
    public static List<Inventory> inventory = new ArrayList<Inventory>();
}
