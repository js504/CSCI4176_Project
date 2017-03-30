package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterNearbyBullietin;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

import static in.geobullet.csci_4176_project.SessionData.currentUser;

public class Nearby_bulletin_boards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_bulletin_boards);


        DatabaseHandler db = new DatabaseHandler(this);


        //user posters as example for now.
        List<Poster> ps = db.getPostersForUser(currentUser.getId());

        Log.i("board", db.getBoardById(SessionData.boardId).getName());
        ListView lv=(ListView) findViewById(R.id.nearby_board_list);
        lv.setAdapter(new CustomAdapterNearbyBullietin(this, ps));

        Log.i("adapter",lv.getAdapter().getItem(0).toString());

        //add click listener to the list view of posters when click call CreateNewPoster activity and pass in poster object
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Nearby_bulletin_boards.this, Main_GUI.class);
                //get the poster by its id
                //Poster selected_poster = db.getPosterById(position);
                //pass selected poster to new intent for user to edit
                //intent.putExtra("posterID", (Serializable) selected_poster);
                startActivity(intent);
            }
        });


    }
}
