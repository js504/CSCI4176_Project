package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.geobullet.csci_4176_project.CustomAdapters.BoardListBaseAdapter;
import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Shared.SessionData;
import in.geobullet.csci_4176_project.CustomAdapters.CustomAdapterPoster;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.User;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Utils.SoundManager;

public class Manage_Posters extends AppCompatActivity {

    private final User currentUser = SessionData.currentUser;
    private final DatabaseHandler db = new DatabaseHandler(this);
    private SoundManager soundManager;

    private Spinner boardSpinner;

    private Integer boardId;

    List<Poster> userPosters;
    List<Board> userBoards;

    HashMap<Integer, List<Poster>> userBoardsAndPosters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_posters);

        soundManager = SoundManager.getInstance();

        boardSpinner = (Spinner) findViewById(R.id.boardSpinner);

        createUserBoardList();
        boardId = -1;

        //create new poster button to invoke the create new poster activity
        Button submit_button = (Button) findViewById(R.id.create_new_poster);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (boardId != -1) {
                    Intent intent = new Intent(Manage_Posters.this, CreateNewPoster.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt(CreateNewPoster.BUNDLE_BOARDID, boardId);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


        boardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Board board = (Board) parent.getItemAtPosition(position);
                boardId = board.getId();

                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        createUserBoardList();
        updateBoardList();
    }

    @Override
    public void onPause() {
        super.onPause();

        soundManager.resetMediaPlayer();
    }


    /**
     * Creates the board and poster list based on the posters that the user has created and that are associted
     * with those boards.
     */
    public void createUserBoardList() {

        List<Board> allBoards = db.getAllBoards();
        userBoardsAndPosters = new HashMap<Integer, List<Poster>>();
        userBoards = new ArrayList<Board>();

        for (int i = 0; i < allBoards.size(); i++) {

            Board board = allBoards.get(i);

            List<Poster> tmpPosters = db.getPostersForBoard(board.getId());

            List<Poster> tmpUserPosters = null;

            for (int j = 0; j < tmpPosters.size(); j++) {

                if (!tmpPosters.isEmpty()) {

                    Poster tmpPoster = tmpPosters.get(j);

                    if (tmpPoster.getCreatedByUserId() == currentUser.getId()) {

                        if (tmpUserPosters == null) {

                            tmpUserPosters = new ArrayList<Poster>();
                        }

                        tmpUserPosters.add(tmpPoster);
                    }

                }
            }

            if (tmpUserPosters != null) {
                userBoardsAndPosters.put(board.getId(), tmpUserPosters);
                userBoards.add(board);
            }
        }


    }


    /**
     * Method updates the board list with boards that have posters associated with the user added to them
     */
    public void updateBoardList() {


        boardSpinner.setAdapter(new BoardListBaseAdapter(this, userBoards));

        boardSpinner.setSelection(0);
        //Board board = (Board)boardSpinner.getItemAtPosition(0);

        //boardId = board.getId();

    }

    /**
     * Function updates the list view of users posters associated with the selected board
     */
    public void updateListView() {

        createUserBoardList();
        userPosters = userBoardsAndPosters.get(boardId);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapterPoster(this, userPosters, db));

    }


}
