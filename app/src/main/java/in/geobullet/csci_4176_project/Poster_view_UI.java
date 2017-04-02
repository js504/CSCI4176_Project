package in.geobullet.csci_4176_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

import static in.geobullet.csci_4176_project.R.drawable.poster_1;

/**
 * Created by darrellread on 2017-03-27.
 */

public class Poster_view_UI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main__gui);
        final DatabaseHandler dbHandler = new DatabaseHandler(this);
        int BOARD_ID = 1;
       // int top_margin_index= 1;
        int left_margin_index = 1;

        Board board = dbHandler.getBoardById(BOARD_ID);

        if (board != null) {

            // todo: Use board to set page title, radius,

            List<Poster> postersForBoard1 = dbHandler.getPostersForBoard(BOARD_ID);

            for (Poster p : postersForBoard1) {

                ImageView iv = new ImageView(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 160);

                layoutParams.setMargins(left_margin_index*50, 100, 0, 0);
                iv.setLayoutParams(layoutParams);

                //top_margin_index++;
                left_margin_index++;
            }
        }
    }
}
