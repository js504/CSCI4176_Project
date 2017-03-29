package in.geobullet.csci_4176_project;

/**
 * Created by tianyewang on 2017-03-22.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Poster;


public class CustomAdapter extends BaseAdapter{
    private String [] posternames;
    private Context context;
    private List<Poster> posters;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Account_info mainActivity, List<Poster> posters) {
        this.posternames =posternames;
        context=mainActivity;
        this.posters =posters;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return posters.size();
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
        holder.tv.setText(posters.get(position).getTitle());

        String name =posters.get(position).getPhotoName();
        name = name.substring(0, name.lastIndexOf("."));
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        Log.i("name", Integer.toString(id));
        //c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
        holder.img.setImageResource(id);
        return listview;
    }

}