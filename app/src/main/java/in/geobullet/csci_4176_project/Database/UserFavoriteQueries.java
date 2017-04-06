package in.geobullet.csci_4176_project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.UserFavorite;


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

    public int getNumUserFavorites() {
        String query = "SELECT COUNT(*) FROM " + DatabaseHandler.TABLE_USER_FAVORITE;

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();

        // (The calling class is responsible for closing the database)
    }

    public List<UserFavorite> getUserFavoritesForUser(int userId) {
        List<UserFavorite> favs = new ArrayList<UserFavorite>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER_FAVORITE +
                " WHERE UserId = " + userId + ";";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                UserFavorite fav = new UserFavorite();

                int idIdx = cursor.getColumnIndex("Id");
                int userIdIdx = cursor.getColumnIndex("UserId");
                int bppIdx = cursor.getColumnIndex("BoardPosterPairId");

                fav.setId(cursor.getInt(idIdx));
                fav.setUserId(cursor.getInt(userIdIdx));
                fav.setBoardPosterPairId(cursor.getInt(bppIdx));

                favs.add(fav);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return favs;
    }
}
