package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterBullietin;
import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;

public class Manage_Bulletins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bulletins);

        final DatabaseHandler db = new DatabaseHandler(this);

        List<Board> bl = db.getAllBoards();
        ListView lv=(ListView) findViewById(R.id.board_list);
        lv.setAdapter(new CustomAdapterBullietin(this, bl));

        //add click listener to the list view of posters when click call CreateNewPoster activity and pass in poster object
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Manage_Bulletins.this, EditBoard.class);
                Board board = db.getBoardById(position+1);

                intent.putExtra("boardID", board.getId());
                startActivity(intent);
            }
        });

    }
}