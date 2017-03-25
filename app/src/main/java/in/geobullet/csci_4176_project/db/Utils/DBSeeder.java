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

        // todo fill in
        String[] posterTitles = {
                "", "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", ""
        };

        // todo set to Event or Service
        PosterType[] posterTypes = {
                PosterType.Event, PosterType.Event, PosterType.Event, PosterType.Event,
                PosterType.Event, PosterType.Event, PosterType.Event, PosterType.Event,
                PosterType.Event, PosterType.Event, PosterType.Event, PosterType.Event,
                PosterType.Event, PosterType.Event, PosterType.Event
        };

        // todo fill in
        String[] posterAddresses = {
                "", "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", ""
        };

        // todo fill in
        String[] posterCities = {
                "", "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", ""
        };

        // todo fill in
        String[] posterStateProvs = {
                "", "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", ""
        };

        // todo fill in
        String[] posterDetails = {
                "", "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", ""
        };

        // todo fill in
        int[] posterStartDateYears = {
                2017, 2017, 2017, 2017,
                2017, 2017, 2017, 2017,
                2017, 2017, 2017, 2017,
                2017, 2017, 2017
        };

        // todo fill in
        int[] posterStartDateMonths = {
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE
        };

        // todo set (1-31)
        int[] posterStartDateDays = {
                20, 20, 20, 20,
                20, 20, 20, 20,
                20, 20, 20, 20,
                20, 20, 20
        };

        // todo set (1-24)
        int[] posterStartDateHours = {
                19, 19, 19, 19,
                19, 19, 19, 19,
                19, 19, 19, 19,
                19, 19, 19
        };

        // todo set (0-59)
        int[] posterStartDateMinutes = {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0
        };

        // todo set
        int[] posterEndDateYears = {
                2017, 2017, 2017, 2017,
                2017, 2017, 2017, 2017,
                2017, 2017, 2017, 2017,
                2017, 2017, 2017
        };

        // todo set
        int[] posterEndDateMonths = {
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE, Calendar.JUNE,
                Calendar.JUNE, Calendar.JUNE, Calendar.JUNE
        };

        // todo set (1-31)
        int[] posterEndDateDays = {
                20, 20, 20, 20,
                20, 20, 20, 20,
                20, 20, 20, 20,
                20, 20, 20
        };

        // todo set (1-24)
        int[] posterEndDateHours = {
                22, 22, 22, 22,
                22, 22, 22, 22,
                22, 22, 22, 22,
                22, 22, 22
        };

        // todo set (0-59)
        int[] posterEndDateMinutes = {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0
        };

        for (int i = 0; i < 15; i++) {
            Poster poster = new Poster();

            Calendar pCal1 = Calendar.getInstance();
            poster.setCreated(pCal1.getTime());
            poster.setCreatedByUserId(currentUser.getId());

            poster.setTitle(posterTitles[i]);
            poster.setPosterType(posterTypes[i]);
            poster.setAddress(posterAddresses[i]);
            poster.setCity(posterCities[i]);
            poster.setStateProv(posterStateProvs[i]);
            poster.setDetails(posterDetails[i]);

            Calendar startDateCal1 = Calendar.getInstance();
            startDateCal1.set(Calendar.YEAR, posterStartDateYears[i]);
            startDateCal1.set(Calendar.MONTH, posterStartDateMonths[i]);
            startDateCal1.set(Calendar.DATE, posterStartDateDays[i]);
            startDateCal1.set(Calendar.HOUR_OF_DAY, posterStartDateHours[i]);
            startDateCal1.set(Calendar.MINUTE, posterStartDateMinutes[i]);
            startDateCal1.set(Calendar.SECOND, 0);

            Calendar endDateCal1 = Calendar.getInstance();
            endDateCal1.set(Calendar.YEAR, posterEndDateYears[i]);
            endDateCal1.set(Calendar.MONTH, posterEndDateMonths[i]);
            endDateCal1.set(Calendar.DATE, posterEndDateDays[i]);
            endDateCal1.set(Calendar.HOUR_OF_DAY, posterEndDateHours[i]);
            endDateCal1.set(Calendar.MINUTE, posterEndDateMinutes[i]);
            endDateCal1.set(Calendar.SECOND, 0);

            poster.setStartDate(startDateCal1.getTime());
            poster.setEndDate(endDateCal1.getTime());

            poster.setStartTime(startDateCal1.getTime());
            poster.setEndTime(endDateCal1.getTime());

            poster.setPhotoName("poster_" + i + ".jpg");

            int posterId = dbHandler.addPoster(poster);

            poster = dbHandler.getPosterById(posterId);

            Log.d("Seeding", "Added poster " + (i + 1) + ": " + poster.toString());

            allPosters.add(poster);
        }

        /*********** End add all posters ****************/


        /*********** Begin all Board Poster Pairs (add all posters to board 1) ************/

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
