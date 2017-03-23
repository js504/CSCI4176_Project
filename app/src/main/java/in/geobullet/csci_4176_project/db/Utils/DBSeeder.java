package in.geobullet.csci_4176_project.db.Utils;

import android.util.Log;

import java.util.Calendar;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.PosterType;
import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

/**
 * Created by Nick on 2017-03-23.
 */

public class DBSeeder {

    public void seedDatabase(DatabaseHandler dbHandler) {

        User u1 = new User();
        u1.setFirstName("Johnny");
        u1.setLastName("B. Goode.");
        u1.setDisplayName("JBG");
        u1.setEmail("JBG@goody.com");
        u1.setPassword("YouGuessedIt");
        u1.setAdmin(true);

        int user1Id = dbHandler.addUser(u1);

        User currentUser = dbHandler.getUserById(user1Id);

        Log.i("Seeding", "Added user: " + currentUser.toString());


        Board b1 = new Board();

        Calendar c1 = Calendar.getInstance(); // current date
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, 2018);

        b1.setCreated(c1.getTime());
        b1.setCreatedByUserId(currentUser.getId());
        b1.setName("Local Bulletin 1");
        b1.setExpirationDate(c2.getTime());
        b1.setRadiusInMeters(1000);
        b1.setLongitude(63.5917);
        b1.setLatitude(44.6366);

        int board1Id = dbHandler.addBoard(b1);

        b1 = dbHandler.getBoardById(board1Id);

        Log.d("Seeding", "Added board: " + b1.toString());


        Poster p1 = new Poster();

        Calendar posterCal1 = Calendar.getInstance();
        p1.setCreated(posterCal1.getTime());
        p1.setCreatedByUserId(currentUser.getId());
        p1.setTitle("A Tribute To Johnny Cash");
        p1.setPosterType(PosterType.Event);
        p1.setAddress("Strathspey Place");
        p1.setCity("Mabou");
        p1.setStateProv("NS");
        p1.setDetails("Who doesn't like Johnny Cash?");

        Calendar startCal1 = Calendar.getInstance();
        startCal1.set(Calendar.YEAR, 2017);
        startCal1.set(Calendar.MONTH, Calendar.JUNE);
        startCal1.set(Calendar.DATE, 20);
        startCal1.set(Calendar.HOUR_OF_DAY, 19); // 7:00 pm
        startCal1.set(Calendar.MINUTE, 0);
        startCal1.set(Calendar.SECOND, 0);

        Calendar endCal1 = (Calendar) startCal1.clone();
        endCal1.set(Calendar.HOUR_OF_DAY, 22); // 10:00 pm
        endCal1.set(Calendar.MINUTE, 0);
        endCal1.set(Calendar.SECOND, 0);

        p1.setStartDate(startCal1.getTime());
        p1.setEndDate(endCal1.getTime());

        p1.setStartTime(startCal1.getTime());
        p1.setEndTime(endCal1.getTime());

        p1.setPhotoName("poster_1.jpg");

        int poster1Id = dbHandler.addPoster(p1);

        p1 = dbHandler.getPosterById(poster1Id);

        Log.d("Seeding", "Added poster 1: " + p1.toString());

        // todo add the rest of the posters


        BoardPosterPair bpp1 = new BoardPosterPair();
        bpp1.setBoardId(board1Id);
        bpp1.setPosterId(poster1Id);

        int bpp1Id = dbHandler.addBoardPosterPair(bpp1);

        bpp1 = dbHandler.getBoardPosterPairById(bpp1Id);

        Log.d("Seeding", "Added board poster pair: " + bpp1.toString());


        // todo finish seeding

    }
}
