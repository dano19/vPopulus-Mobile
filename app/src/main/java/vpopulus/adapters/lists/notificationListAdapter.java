package vpopulus.adapters.lists;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import vpopulus.mobile.R;
import vpopulus.model.backend.Cache;
import vpopulus.model.backend.DownloadImageTask;

/**
 * Created by dano19 on 29/07/2015.
 */
public class notificationListAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private static LayoutInflater inflater=null;
    public Resources res;
    int i = 0;

    /*************  CustomAdapter Constructor *****************/
    public notificationListAdapter(Activity a, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(Cache.notifications.size() <= 0)
            return 1;
        return Cache.notifications.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView t1;
        public TextView t2;
    }

    public View getView(int position, final View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.adapter_list_notifications, null);

            holder = new ViewHolder();
            holder.t1 = (TextView) vi.findViewById(R.id.notificationText);
            holder.t2 = (TextView)vi.findViewById(R.id.notificationTimestamp);

            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(Cache.notifications.size() <= 0)
        {
            holder.t1.setText("No notifications found.");
            holder.t2.setText("");
        }
        else
        {
            holder.t1.setText(Cache.notifications.get(position).message);
            holder.t2.setText("00/00/0000 00:00");
        }
        return vi;
    }
}

