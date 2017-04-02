package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterBullietin;
import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

import static in.geobullet.csci_4176_project.SessionData.currentUser;

public class Manage_Bulletins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bulletins);

        DatabaseHandler db = new DatabaseHandler(this);

        List<Board> bl = db.getAllBoards();
        ListView lv=(ListView) findViewById(R.id.board_list);
        lv.setAdapter(new CustomAdapterBullietin(this, bl));

        //add click listener to the list view of posters when click call CreateNewPoster activity and pass in poster object
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Manage_Bulletins.this, CreateNewPoster.class);
                //get the poster by its id
                //Poster selected_poster = db.getPosterById(position);
                //pass selected poster to new intent for user to edit
                //intent.putExtra("posterID", (Serializable) selected_poster);
                startActivity(intent);
            }
        });

    }
}
