package in.geobullet.csci_4176_project.CustomAdapters;

/**
 * Created by tianyewang on 2017-03-22.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import in.geobullet.csci_4176_project.CreateNewPoster;
import in.geobullet.csci_4176_project.Database.BoardPosterPairQueries;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.MainActivity;
import in.geobullet.csci_4176_project.Manage_Posters;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Database.Classes.Poster;


public class CustomAdapterPoster extends BaseAdapter{
    private String [] posternames;
    private Context context;
    private List<Poster> posters;
    private static LayoutInflater inflater=null;

    private Manage_Posters mainActivity;
    private DatabaseHandler db;

    public CustomAdapterPoster(Manage_Posters mainActivity, List<Poster> posters, DatabaseHandler db) {
        this.mainActivity = mainActivity;
        this.db = db;
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
    public Poster getItem(int position) {
        return posters.get(position);
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



        SwipeLayout swipeLayout =  (SwipeLayout)listview.findViewById(R.id.swipe_layout);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, listview.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });


        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Poster poster = posters.get(position);

                Bundle editPosterBundle = new Bundle();
                editPosterBundle.putInt(CreateNewPoster.BUNDLE_ID, poster.getId());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_TITLE, poster.getTitle());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_TYPE, poster.getPosterType().toString());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_ADDRESS, poster.getAddress());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_CITY, poster.getCity());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_STARTDATE, poster.getStartDate().toString());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_ENDDATE, poster.getEndDate().toString());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_STARTTIME, poster.getStartTime().toString());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_ENDTIME, poster.getEndTime().toString());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_DETAILS, poster.getDetails());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_IMGSRC, poster.getPhotoName());
                editPosterBundle.putString(CreateNewPoster.BUNDLE_IMGICONSRC, poster.getIconName());

                Intent intent = new Intent(mainActivity, CreateNewPoster.class);
                intent.putExtras(editPosterBundle);

                mainActivity.startActivity(intent);
            }
        });

        swipeLayout.findViewById(R.id.bottom_wrapper).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int posterId = posters.get(position).getId();
                db.removePosterBoardPairByPosterId(posterId);
                mainActivity.updateListView();
                Toast.makeText(mainActivity, "Poster Deleted!", Toast.LENGTH_SHORT);
            }
        });


        holder.tv=(TextView) listview.findViewById(R.id.textView1);
        holder.img=(ImageView) listview.findViewById(R.id.imageView1);
        holder.tv.setText(posters.get(position).getTitle());
        String name =posters.get(position).getIconName();
        name = name.substring(0, name.lastIndexOf("."));
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        Log.i("name", Integer.toString(id));
        //c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
        holder.img.setImageResource(id);


        return listview;
    }

}