package in.geobullet.csci_4176_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.HorizontalScrollView;

import java.util.List;

import in.geobullet.csci_4176_project.db.BoardQueries;
import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.PosterQueries;

public class Main_GUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui);

        int BOARD_ID = 1;

        BoardQueries boardQueries = new BoardQueries(this);
        PosterQueries posterQueries = new PosterQueries(this);

        Board board = boardQueries.getBoardById(BOARD_ID);
        // use board to set page title, radius,

        List<Poster> postersForBoard1 = posterQueries.getPostersByBoardId(BOARD_ID);

        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

        for (Poster p: postersForBoard1) {
            //ImageView iv = new ImageView();

            // something like this
            //iv.setImage(p.getPhotoName());

            //hScrollView.addView(iv);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
