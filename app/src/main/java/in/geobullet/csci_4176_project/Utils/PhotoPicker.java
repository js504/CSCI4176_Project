package in.geobullet.csci_4176_project.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.PhotoPickerBaseAdapter;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.R;

import static in.geobullet.csci_4176_project.Shared.SessionData.currentUser;

public class PhotoPicker extends AppCompatActivity {

    private DatabaseHandler db;

    private GridView gridView;

    public static final int NUM_PHOTOS = 15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        db = new DatabaseHandler(this);

        gridView = (GridView)findViewById(R.id.photo_picker_grid_view);

       // List<Poster> posters = db.getAllPosters();

        //Default poster creation based on posters locally stored in app since no server is used
        List<String> posters = new ArrayList<String>();
        for(int i = 0; i < NUM_PHOTOS; i++){
            posters.add("poster_" + (i+1) + "_icon.png");

        }


        if(posters == null){
            Log.i("POSTERS COUNT", "NULL");

        }
        else{
            Log.i("POSTERS COUNT", "COUNT: " + posters.size());
        }

        gridView.setAdapter(new PhotoPickerBaseAdapter(this, posters));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Poster poster = (Poster)parent.getItemAtPosition(position);

                String imgSrc = poster.getPhotoName();
                imgSrc = imgSrc.substring(0, imgSrc.lastIndexOf("."));

                Intent result = new Intent();
                result.putExtra("IMG_SRC", imgSrc);
                setResult(Activity.RESULT_OK, result);
                finish();
            }

        });

    }
}
