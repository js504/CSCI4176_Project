package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<BoardPosterPair> getAllBoardPosterPairs() {
        List<BoardPosterPair> bpps = new ArrayList<>();

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

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

        db.close();

        return bpps;
    }


}
