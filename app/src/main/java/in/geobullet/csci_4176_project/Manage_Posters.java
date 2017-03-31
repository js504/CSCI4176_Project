package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterPoster;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Manage_Posters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_posters);

        final DatabaseHandler db = new DatabaseHandler(this);


        final User currentUser = SessionData.currentUser;
        List<Poster> userPoster = null;
        userPoster = db.getPostersForUser(currentUser.getId());


        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapterPoster(this, userPoster));

        //add click listener to the list view of posters when click call CreateNewPoster activity and pass in poster object
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Manage_Posters.this, CreateNewPoster.class);
                //get the poster by its id
                //Poster selected_poster = db.getPosterById(position);
                //pass selected poster to new intent for user to edit
                //intent.putExtra("posterID", (Serializable) selected_poster);
                startActivity(intent);
            }
        });

        //create new poster button to invoke the create new poster activity
        Button submit_button = (Button) findViewById(R.id.create_new_poster);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Manage_Posters.this, CreateNewPoster.class);
                startActivity(intent);
            }
        });

    }
}
