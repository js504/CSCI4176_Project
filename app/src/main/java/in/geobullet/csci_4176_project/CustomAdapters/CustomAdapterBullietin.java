package in.geobullet.csci_4176_project.CustomAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.geobullet.csci_4176_project.Manage_Bulletins;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.Classes.Poster;


public class CustomAdapterBullietin extends BaseAdapter {
    private Context context;
    private List<Poster> posters;
    private List<Board> board;
    private LayoutInflater inflater = null;

    public CustomAdapterBullietin(Manage_Bulletins mainActivity, List<Board> board) {
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

    private class Holder {
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
        holder.img.setImageResource(R.drawable.pin);
        return listview;
    }

}