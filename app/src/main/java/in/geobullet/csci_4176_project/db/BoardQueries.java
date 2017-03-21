package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import java.sql.Date;

import in.geobullet.csci_4176_project.db.Classes.Board;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardQueries {

    private SQLiteDatabase db;

    public BoardQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public void addBoard(Board board) {
        ContentValues vals = new ContentValues();

        vals.put("Created", DateFormat.format("yyyy-MM-dd HH:mm:ss", board.getCreated()).toString());
        vals.put("CreatedByUserId", board.getCreatedByUserId());
        vals.put("Name", board.getName());
        vals.put("ExpirationDate", DateFormat.format("yyyy-MM-dd HH:mm:ss", board.getExpirationDate()).toString());
        vals.put("RadiusInMeters", board.getRadiusInMeters());
        vals.put("Longitude", board.getLongitude());
        vals.put("Latitude", board.getLatitude());

        db.insert(DatabaseHandler.TABLE_BOARD, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public Board getBoardById(int id) {
        String query = "SELECT * FROM Board where Id = " + id + ";";

        Cursor cursor = db.rawQuery(query, null);

        Board b = null;

        if (cursor.moveToFirst()) {
            do {
                b = new Board();

                int createdIdx = cursor.getColumnIndex("Created");
                int cbuidIdx = cursor.getColumnIndex("CreatedByUserId");
                int nameIdx = cursor.getColumnIndex("Name");
                int expIdx = cursor.getColumnIndex("ExpirationDate");
                int radIdx = cursor.getColumnIndex("RadiusInMeters");
                int longIdx = cursor.getColumnIndex("Longitude");
                int latIdx = cursor.getColumnIndex("Latitude");

                b.setCreated(Date.valueOf(cursor.getString(createdIdx)));
                b.setCreatedByUserId(cursor.getInt(cbuidIdx));
                b.setName(cursor.getString(nameIdx));
                b.setExpirationDate(Date.valueOf(cursor.getString(expIdx)));
                b.setRadiusInMeters(cursor.getInt(radIdx));
                b.setLongitude(cursor.getDouble(longIdx));
                b.setLatitude(cursor.getDouble(latIdx));

            } while (cursor.moveToNext());
        }

        return b;

        // (The calling class is responsible for closing the database)
    }


    // todo deleteBoard, getAllBoards, getBoardByLatitudeLongitudeWithinMeters

}
