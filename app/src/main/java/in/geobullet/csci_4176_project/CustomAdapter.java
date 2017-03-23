package in.geobullet.csci_4176_project;

/**
 * Created by tianyewang on 2017-03-22.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter{
    private String [] posternames;
    private Context context;
    private int [] posters;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Account_info mainActivity, String[] posternames, int[] posters) {
        this.posternames =posternames;
        context=mainActivity;
        this.posters =posters;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return posternames.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View listview;
        listview = inflater.inflate(R.layout.custom_poster_listview, null);
        holder.tv=(TextView) listview.findViewById(R.id.textView1);
        holder.img=(ImageView) listview.findViewById(R.id.imageView1);
        holder.tv.setText(posternames[position]);
        holder.img.setImageResource(posters[position]);
        listview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+ posternames[position], Toast.LENGTH_LONG).show();
                // TODO: 2017-03-22  edit poster here
            }
        });
        return listview;
    }

}