package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import in.geobullet.csci_4176_project.Shared.SessionData;
import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterPoster;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.User;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;

public class Manage_Posters extends AppCompatActivity {

    final User currentUser = SessionData.currentUser;
    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_posters);

        //create new poster button to invoke the create new poster activity
        Button submit_button = (Button) findViewById(R.id.create_new_poster);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Manage_Posters.this, CreateNewPoster.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        updateListView();
    }

    public void updateListView(){
        List<Poster> userPoster = null;
        userPoster = db.getPostersForUser(currentUser.getId());

        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapterPoster(this, userPoster, db));

        //add click listener to the list view of posters when click call CreateNewPoster activity and pass in poster object
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Poster poster = (Poster)parent.getItemAtPosition(position);
//
//                if(poster != null) {
//                    Bundle editPosterBundle = new Bundle();
//                    editPosterBundle.putInt("ID", poster.getId());
//                    editPosterBundle.putString("TITLE", poster.getTitle());
//                    editPosterBundle.putString("ADDRESS", poster.getAddress());
//                    editPosterBundle.putString("CITY", poster.getCity());
//                    editPosterBundle.putString("STARTDATE", poster.getStartDate().toString());
//                    editPosterBundle.putString("ENDDATE", poster.getEndDate().toString());
//                    editPosterBundle.putString("STARTTIME", poster.getStartTime().toString());
//                    editPosterBundle.putString("ENDTIME", poster.getEndTime().toString());
//                    editPosterBundle.putString("DETAILS", poster.getDetails());
//                    editPosterBundle.putString("IMGSRC", poster.getPhotoName());
//                    editPosterBundle.putString("IMGICONSRC", poster.getIconName());
//
//                    Log.i("Getting Poster", poster.getStartDate().toString());
//
////                    Intent intent = new Intent(Manage_Posters.this, CreateNewPoster.class);
////                    intent.putExtras(editPosterBundle);
////
////                    startActivity(intent);
//                }
//            }
//        });

    }
}
