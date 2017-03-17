package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.UserFavorite;

/**
 * Created by Nick on 2017-03-15.
 */

public class UserFavoriteQueries {

    private DatabaseHandler dbHandler;

    public UserFavoriteQueries(Context c) {
        dbHandler = new DatabaseHandler(c);
    }

    public void addUserFavorite(UserFavorite userFav) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("UserId", userFav.getUserId());
        vals.put("BoardPosterPairId", userFav.getBoardPosterPairId());

        db.insert(DatabaseHandler.TABLE_USER_FAVORITE, null, vals);

        db.close();
    }

    public List<UserFavorite> getUserFavoritesByUserId(int userId) {
        List<UserFavorite> favs = new ArrayList<UserFavorite>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER_FAVORITE +
                " WHERE UserId = " + userId + ";";

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                UserFavorite fav = new UserFavorite();

                int userIdIndex = cursor.getColumnIndex("UserId");
                int bppIndex = cursor.getColumnIndex("BoardPosterPairId");

                fav.setUserId(Integer.parseInt(cursor.getString(userIdIndex)));
                fav.setBoardPosterPairId(Integer.parseInt(cursor.getString(bppIndex)));

                favs.add(fav);

            } while (cursor.moveToNext());
        }

        db.close();

        return favs;
    }
}
