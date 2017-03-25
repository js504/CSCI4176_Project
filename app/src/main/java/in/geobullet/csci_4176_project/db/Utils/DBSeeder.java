package in.geobullet.csci_4176_project.db.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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


        List<Poster> allPosters = new ArrayList<>();


        /*********** Begin add all posters ****************/

        // todo: [set the title, details etc of the posters individually.
        // todo: Put all the titles in an array, details in an array, then grab them
        // todo: by index and set them here to reduce code duplication]

        for (int i = 1; i <= 15; i++) {
            Poster poster = new Poster();

            Calendar pCal1 = Calendar.getInstance();
            poster.setCreated(pCal1.getTime());
            poster.setCreatedByUserId(currentUser.getId());

            poster.setTitle(""); // todo
            poster.setPosterType(PosterType.Event); // todo
            poster.setAddress(""); // todo
            poster.setCity(""); // todo
            poster.setStateProv(""); // todo
            poster.setDetails(""); // todo

            Calendar startDateCal1 = Calendar.getInstance();
            startDateCal1.set(Calendar.YEAR, 2017); // todo
            startDateCal1.set(Calendar.MONTH, Calendar.JUNE); // todo
            startDateCal1.set(Calendar.DATE, 20); // todo
            startDateCal1.set(Calendar.HOUR_OF_DAY, 19); // 7:00 pm // todo
            startDateCal1.set(Calendar.MINUTE, 0); // todo
            startDateCal1.set(Calendar.SECOND, 0);

            Calendar endDateCal1 = (Calendar) startDateCal1.clone();
            endDateCal1.set(Calendar.HOUR_OF_DAY, 22); // 10:00 pm // todo
            endDateCal1.set(Calendar.MINUTE, 0); // todo
            endDateCal1.set(Calendar.SECOND, 0);

            poster.setStartDate(startDateCal1.getTime());
            poster.setEndDate(endDateCal1.getTime());

            poster.setStartTime(startDateCal1.getTime());
            poster.setEndTime(endDateCal1.getTime());

            poster.setPhotoName("poster_" + i + ".jpg");

            int posterId = dbHandler.addPoster(poster);

            poster = dbHandler.getPosterById(posterId);

            Log.d("Seeding", "Added poster " + i + ": " + poster.toString());

            allPosters.add(poster);
        }

        /*********** End add all posters ****************/


        /*********** Begin all Board Poster Pairs (add all postesr to board 1) ************/

        for (Poster p: allPosters) {

            BoardPosterPair bpp = new BoardPosterPair();

            bpp.setBoardId(board1Id);
            bpp.setPosterId(p.getId());

            int bppId = dbHandler.addBoardPosterPair(bpp);

            bpp = dbHandler.getBoardPosterPairById(bppId);

            Log.d("Seeding", "Added board poster pair: " + bpp.toString());
        }

        /*********** End all Board Poster Pairs ************/



        // todo finish seeding

    }
}
