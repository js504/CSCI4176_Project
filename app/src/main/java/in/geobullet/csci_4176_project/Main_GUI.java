package in.geobullet.csci_4176_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.HorizontalScrollView;

import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Main_GUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui);

        int BOARD_ID = 1;

        final DatabaseHandler dbHandler = new DatabaseHandler(this);

        Board board = dbHandler.getBoardById(BOARD_ID);

        if (board != null) {

            // todo: Use board to set page title, radius,

            List<Poster> postersForBoard1 = dbHandler.getPostersByBoardId(BOARD_ID);

            HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

            for (Poster p: postersForBoard1) {
                //ImageView iv = new ImageView();

                // something like this
                //iv.setImage(p.getPhotoName());

                //hScrollView.addView(iv);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
