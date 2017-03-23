package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.UserFavorite;

/**
 * Created by Nick on 2017-03-15.
 */

public class UserFavoriteQueries {

    private SQLiteDatabase db;

    public UserFavoriteQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public long addUserFavorite(UserFavorite userFav) {
        ContentValues vals = new ContentValues();

        vals.put("UserId", userFav.getUserId());
        vals.put("BoardPosterPairId", userFav.getBoardPosterPairId());

        return db.insert(DatabaseHandler.TABLE_USER_FAVORITE, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public List<UserFavorite> getUserFavoritesByUserId(int userId) {
        List<UserFavorite> favs = new ArrayList<UserFavorite>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER_FAVORITE +
                " WHERE UserId = " + userId + ";";

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

        // (The calling class is responsible for closing the database)

        return favs;
    }
}
