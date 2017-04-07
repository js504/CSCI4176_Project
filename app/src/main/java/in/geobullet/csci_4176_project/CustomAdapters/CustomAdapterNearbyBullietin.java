package in.geobullet.csci_4176_project.CustomAdapters;

/**
 * Created by tianyewang on 2017-03-22.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.geobullet.csci_4176_project.Nearby_bulletin_boards;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.Classes.Poster;


public class CustomAdapterNearbyBullietin extends BaseAdapter {
    private String[] posternames;
    private Context context;
    private List<Poster> posters;
    private List<Board> board;
    private static LayoutInflater inflater = null;

    public CustomAdapterNearbyBullietin(Nearby_bulletin_boards mainActivity, List<Board> board) {
        this.posternames = posternames;
        context = mainActivity;
        this.board = board;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return board.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View listview;
        listview = inflater.inflate(R.layout.custom_board_listview, null);
        holder.tv = (TextView) listview.findViewById(R.id.boardtext);
        holder.img = (ImageView) listview.findViewById(R.id.boardimage);
        holder.tv.setText(board.get(position).getName());
        //c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
        holder.img.setImageResource(R.drawable.pin);
        return listview;
    }

}