package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterNearbyBullietin;
import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Shared.SessionData;

public class Nearby_bulletin_boards extends AppCompatActivity {
    private int progress = 0;
    List<Board> bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_bulletin_boards);

        DatabaseHandler db = new DatabaseHandler(this);

        if(SessionData.location == null && SessionData.radius == -1 || progress == 100000){
            bl = db.getAllBoards();
        }else{
            bl = db.searchAllBoardsWithinMetersOfGivenLatitudeLongitude(SessionData.radius, SessionData.location.getLatitude(), SessionData.location.getLongitude());
        }

        ListView lv=(ListView) findViewById(R.id.nearby_board_list);
        lv.setAdapter(new CustomAdapterNearbyBullietin(this, bl));

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

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textView = (TextView) findViewById(R.id.textView_seek);

        textView.setText("Location: +" + 50 + " Meters");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                String suffix = "";
                boolean isNational = false;

                if (0 <= progresValue && progresValue <= 5) {
                    progress = 50;
                } else if (5 < progresValue && progresValue <= 10) {
                    progress = 100;
                    suffix = "Meters";
                } else if (10 < progresValue && progresValue <= 15) {
                    progress = 200;
                    suffix = "Meters";
                } else if (15 < progresValue && progresValue <= 20) {
                    progress = 500;
                    suffix = "Meters";
                } else if (20 < progresValue && progresValue <= 30) {
                    progress = 1000;
                    suffix = "Meters";
                } else if (30 < progresValue && progresValue <= 40) {
                    progress = 2000;
                    suffix = "Meters";
                } else if (40 < progresValue && progresValue <= 50) {
                    progress = 5000;
                    suffix = "Meters";
                } else if (50 < progresValue && progresValue <= 70) {
                    progress = 10000;
                    suffix = "Meters";
                } else if (70 < progresValue && progresValue <= 90) {
                    progress = 20000;
                    suffix = "Meters";
                } else if (90 < progresValue && progresValue <= 100) {
                    progress = 100000;
                    isNational = true;
                }

                String text;
                if (isNational) {
                    text = "Location: National";
                } else {
                    text = "Location: +" + progress + " " + suffix;
                }

                textView.setText(text);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button update_radius = (Button) findViewById(R.id.modify_radius);
        //update the board information
        update_radius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SessionData.location == null){
                    Toast.makeText(Nearby_bulletin_boards.this, "Location are not avaliable",
                            Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(Nearby_bulletin_boards.this, Nearby_bulletin_boards.class);
                    SessionData.radius = progress;
                    finish();
                    startActivity(intent);
                }
            }
        });

    }
}
