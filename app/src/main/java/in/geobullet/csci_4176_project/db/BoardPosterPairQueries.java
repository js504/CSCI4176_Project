package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardPosterPairQueries {

    private SQLiteDatabase db;

    public BoardPosterPairQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public void addBoardPosterPair(BoardPosterPair bpp) {
        ContentValues vals = new ContentValues();

        vals.put("BoardId", bpp.getBoardId());
        vals.put("PosterId", bpp.getPosterId());

        db.insert(DatabaseHandler.TABLE_BOARD_POSTER_PAIR, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public List<BoardPosterPair> getAllBoardPosterPairs() {
        List<BoardPosterPair> bpps = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD_POSTER_PAIR + ";";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BoardPosterPair bpp = new BoardPosterPair();

                int boardIdIndex = cursor.getColumnIndex("BoardId");
                int posterIdIndex = cursor.getColumnIndex("PosterId");

                bpp.setBoardId(Integer.parseInt(cursor.getString(boardIdIndex)));
                bpp.setPosterId(Integer.parseInt(cursor.getString(posterIdIndex)));

                bpps.add(bpp);

            } while (cursor.moveToNext());
        }

        return bpps;

        // (The calling class is responsible for closing the database)
    }


}
