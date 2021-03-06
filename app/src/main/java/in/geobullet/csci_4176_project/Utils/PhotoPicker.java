package in.geobullet.csci_4176_project.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.PhotoPickerBaseAdapter;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.R;


/**
 *
 * Class creates a basic photo pickers that allows users to select from a list of images locally stored within the application for
 * use with the creation of posters.  This would have been hooked up to a server if that were part of the assignment.
 *
 */

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

        //Default poster creation based on posters locally stored in app since no server is used
        List<String> posters = new ArrayList<String>();
        for(int i = 0; i < NUM_PHOTOS; i++){
            posters.add("poster_" + (i+1) + "_icon.png");

        }

        //set the custom adapter to gridview
        gridView.setAdapter(new PhotoPickerBaseAdapter(this, posters));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            /**
             * gets the img icon source from the photo picker and truncates it to match the normal
             * image name, then sends that back to calling activity.
             *
             * @param parent        The caller
             * @param view          The current selected view
             * @param position      The position in the grid
             * @param id            The id of item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgSrc = (String)parent.getItemAtPosition(position);

                imgSrc = imgSrc.substring(0, imgSrc.lastIndexOf("."));
                imgSrc = imgSrc.substring(0, imgSrc.lastIndexOf("_"));


                Intent result = new Intent();
                result.putExtra("IMG_SRC", imgSrc);
                setResult(Activity.RESULT_OK, result);
                finish();
            }

        });

    }
}
