package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                finish();
                startActivity(intent);
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final TextView textView = (TextView) findViewById(R.id.textView_seek);

        int posterRadius = SessionData.posterSearchRadiusInMeters;
        int prog = 0;

        if (posterRadius <= 50) {
            prog = 20;
        } else if (posterRadius <= 100) {
            prog = 40;
        } else if (posterRadius <= 250) {
            prog = 60;
        } else if (posterRadius <= 500) {
            prog = 80;
        } else if (posterRadius <= 1000) {
            prog = 100;
        }

        textView.setText("Search within: " + posterRadius + " meters");
        seekBar.setProgress(prog);
        progress = prog;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVal, boolean fromUser) {

                int radius = 0;

                if (progressVal <= 20) {
                    radius = 50;
                } else if (progressVal <= 40) {
                    radius = 100;
                } else if (progressVal <= 60) {
                    radius = 250;
                } else if (progressVal <= 80) {
                    radius = 500;
                } else if (progressVal <= 100) {
                    radius = 1000;
                }

                SessionData.posterSearchRadiusInMeters = radius;
                progress = radius;
                textView.setText("Search within: " + radius + " meters");
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
