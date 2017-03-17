package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import in.geobullet.csci_4176_project.db.Classes.Board;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardQueries {

    private DatabaseHandler dbHandler;

    public BoardQueries(Context c) {
        dbHandler = new DatabaseHandler(c);
    }

    public void addBoard(Board board) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("Created", board.getCreated().toString());
        vals.put("CreatedByUserId", board.getCreatedByUserId());
        vals.put("Name", board.getName());
        vals.put("ExpirationDate", board.getExpirationDate().toString());
        vals.put("RadiusInMeters", board.getRadiusInMeters());
        vals.put("Longitude", board.getLongitude());
        vals.put("Latitude", board.getLatitude());

        db.insert(DatabaseHandler.TABLE_BOARD, null, vals);

        db.close();
    }

    public Board getBoardById(int id) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        return null;
    }

    // todo deleteBoard, getAllBoards, getBoardByLatitudeLongitudeWithinMeters

}
