package in.geobullet.csci_4176_project.CustomAdapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Manage_Posters;
import in.geobullet.csci_4176_project.R;

/**
 * Created by jt on 2017-04-07.
 */

public class BoardListBaseAdapter extends BaseAdapter {


    private List<Board> boards;
    private Manage_Posters context;
    private LayoutInflater layoutInflater;

    public BoardListBaseAdapter(Manage_Posters managePosters, List<Board> boards) {
        context = managePosters;
        this.boards = boards;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Object getItem(int position) {
        return boards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return boards.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_board_listview, null);
        }

        TextView boardName = (TextView) convertView.findViewById(R.id.boardtext);

        if (!boards.isEmpty()) {
            Board board = boards.get(position);
            boardName.setText(board.getName());
        }


        return convertView;
    }
}
