package in.geobullet.csci_4176_project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.BoardPosterPair;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardPosterPairQueries {

    private SQLiteDatabase db;

    public BoardPosterPairQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public long addBoardPosterPair(BoardPosterPair bpp) {
        ContentValues vals = new ContentValues();

        vals.put("BoardId", bpp.getBoardId());
        vals.put("PosterId", bpp.getPosterId());

        return db.insert(DatabaseHandler.TABLE_BOARD_POSTER_PAIR, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public int getNumBoardPosterPairs() {
        String query = "SELECT COUNT(*) FROM " + DatabaseHandler.TABLE_BOARD_POSTER_PAIR;

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();

        // (The calling class is responsible for closing the database)
    }

    public BoardPosterPair getBoardPosterPairById(int bppId) {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD_POSTER_PAIR + " WHERE Id = " + bppId + ";";

        Cursor cursor = db.rawQuery(query, null);

        BoardPosterPair bpp = null;

        if (cursor.moveToFirst()) {
            do {
                bpp = new BoardPosterPair();

                this.setBoardPosterPairFields(cursor, bpp);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return bpp;
    }

    public List<BoardPosterPair> getAllBoardPosterPairs() {
        List<BoardPosterPair> bpps = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD_POSTER_PAIR + ";";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BoardPosterPair bpp = new BoardPosterPair();

                bpp = this.setBoardPosterPairFields(cursor, bpp);

                bpps.add(bpp);

            } while (cursor.moveToNext());
        }

        return bpps;

        // (The calling class is responsible for closing the database)
    }

    private BoardPosterPair setBoardPosterPairFields(Cursor cursor, BoardPosterPair bpp) {
        if (bpp == null) {
            bpp = new BoardPosterPair();
        }

        int idIdx = cursor.getColumnIndex("Id");
        int boardIdIdx = cursor.getColumnIndex("BoardId");
        int posterIdIdx = cursor.getColumnIndex("PosterId");

        bpp.setId(cursor.getInt(idIdx));
        bpp.setBoardId(cursor.getInt(boardIdIdx));
        bpp.setPosterId(cursor.getInt(posterIdIdx));

        return bpp;
    }

}
