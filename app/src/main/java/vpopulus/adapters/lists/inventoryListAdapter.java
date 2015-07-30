package vpopulus.adapters.lists;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vpopulus.mobile.R;
import vpopulus.model.backend.Cache;
import vpopulus.model.backend.DownloadImageTask;

/**
 * Created by dano19 on 29/07/2015.
 */
public class inventoryListAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private static LayoutInflater inflater=null;
    public Resources res;
    int i = 0;

    /*************  CustomAdapter Constructor *****************/
    public inventoryListAdapter(Activity a, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(Cache.inventory.size() <= 0)
            return 1;
        return Cache.inventory.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public ImageView t1;
        public TextView t2;
        public TextView t3;
    }

    public View getView(int position, final View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.adapter_list_inventory, null);

            holder = new ViewHolder();
            holder.t1 = (ImageView) vi.findViewById(R.id.inventoryIcon);
            holder.t2 = (TextView)vi.findViewById(R.id.inventoryName);
            holder.t3 = (TextView)vi.findViewById(R.id.inventoryAmount);

            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(Cache.inventory.size() <= 0)
        {
           // holder.t2.setText("No items found.");
            //holder.t3.setText("");
        }
        else
        {
            new DownloadImageTask(holder.t1).execute("http://www.vpopulus.net/assets/img/ico/prod/" + Cache.inventory.get(position).itemName.replace(' ', '-') + ".png");
            holder.t2.setText(Cache.inventory.get(position).itemName);
            holder.t3.setText("" + Cache.inventory.get(position).amount);
        }
        return vi;
    }
}

