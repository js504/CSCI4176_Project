package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardPosterPairQueries {

    private DatabaseHandler dbHandler;

    public BoardPosterPairQueries(Context c) {
        dbHandler = new DatabaseHandler(c);
    }

    public void addBoardPosterPair(BoardPosterPair bpp) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("BoardId", bpp.getBoardId());
        vals.put("PosterId", bpp.getPosterId());

        db.insert(DatabaseHandler.TABLE_BOARD_POSTER_PAIR, null, vals);

        db.close();
    }




}
