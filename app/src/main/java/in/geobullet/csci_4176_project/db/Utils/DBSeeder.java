package in.geobullet.csci_4176_project.db.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.geobullet.csci_4176_project.MainActivity;
import in.geobullet.csci_4176_project.SessionData;
import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.PosterType;
import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

/**
 * Created by Nick on 2017-03-23.
 * Darrell to add
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
        b1.setLongitude(-63.57523);
        b1.setLatitude(44.64876);
        //add this to display board created by user ID
        b1.setCreatedByUserId(u1.getId());

        int board1Id = dbHandler.addBoard(b1);

        //Added boardId to SessionData class
        SessionData.boardId = board1Id;

        b1 = dbHandler.getBoardById(board1Id);

        Log.d("Seeding", "Added board: " + b1.toString());


        List<Poster> allPosters = new ArrayList<>();



        /*********** Begin add all posters ****************/

        // todo fill in
        String[] posterTitles = {
                "QueenPins Spring Fundraising Event"
                , "Entreprenurial Development Conference & Expo"
                , "Business Case Writing Training"
                , "Project Management Professional Certification Training"
                , "Benefits of Hiring Immigrants"
                , "Air Traffic Control"
                , "HOPSIN - Borderline Tour w/ TOKEN"
                , "Broadway Hits Dartmouth"
                , "Keloose Paint The Town!"
                , "Shokran Canada 2017"
                , "Artists of Dalhousie Exhibition Launch!"
                , "KAMP cabaret take-over"
                , "Spring Geequinox ~Version 4.0~"
                , "Learn to Meditate"
                , "IELTS Preparation"
        };

        // todo set to Event or Service
        PosterType[] posterTypes = {
                PosterType.Event, PosterType.Event, PosterType.Service, PosterType.Service,
                PosterType.Event, PosterType.Event, PosterType.Event, PosterType.Event,
                PosterType.Event, PosterType.Event, PosterType.Event, PosterType.Event,
                PosterType.Event, PosterType.Service, PosterType.Service
        };

        // todo fill in
        String[] posterAddresses = {
                "Brightwood Golf & Country Club 227 School St.",
                "World Trade Convention Centre 1800 Argyle St.",
                "Regus Halifax 1959 Upper Water St. #1301",
                "Regus Halifax 1959 Upper Water St. #1301",
                "Halifax Exhibition Centre 200 Prospect Rd.",
                "Grand Banker Bar & Grill 82 Montague St.",
                "Marquee/Seahorse Complex 2037 Gottingen St",
                "Grace United Church 70 King Street ",
                "843 Fall River Road",
                "Dalhousie Student Union 6136 University Avenue ",
                "6101 University Ave",
                "Kamphyre Studios 6070 Almon Street ",
                "Halifax Forum Windsor Street ",
                "1 Cedarbrae Ln",
                "1256 Barrington St"
        };

        // todo fill in
        String[] posterCities = {
                "Dartmouth", "Halifax", "Halifax", "Halifax", "Halifax",
                "Halifax", "Halifax", "Dartmouth", "Fall River", "Halifax",
                "Halifax", "Halifax", "Halifax", "Halifax", "Halifax"
        };

        // todo fill in
        String[] posterStateProvs = {
                "NS", "NS", "NS", "NS", "NS",
                "NS", "NS", "NS", "NS", "NS",
                "NS", "NS", "NS", "NS", "NS"
        };

        // todo fill in
        String[] posterDetails = {
                "Join us for 3 hours of high energy fun while we host three \n" +
                        "incredible speakers, showcase local businesses, and connect \n" +
                        "powerful women (and their supporters) to other powerful \n" +
                        "people within the community. All this while raising funds for \n" +
                        "Habitat for Humanity Nova Scotia!",

                "Join your peers at Atlantic Canada's #1 business conference, \n" +
                        "expo, and networking event of the year! EDCE was created \n" +
                        "to help new and seasoned entrepreneurs take charge of their \n" +
                        "business. Our 1.5-day event offers valuable insights and \n" +
                        "exclusive opportunities with over 20 in-depth sessions, \n" +
                        "workshops, and presentations from top industry experts. \n" +
                        "We couple this content with an interactive Small Business \n" +
                        "expo and 3 one-on-one networking events to give you \n" +
                        "everything you'll need to address the ever changing business \n" +
                        "landscapes.",

                "This one day program will introduce participants to the \n" +
                        "principles of developing an effective Business Case, within \n" +
                        "the context of an interactive course driven by a case study.\n" +
                        "This workshop will provide participants with a working \n" +
                        "knowledge of the principles of writing an effective, \n" +
                        "comprehensive and compelling Business Case.\n" +
                        "The course is driven by participation in a case study, \n" +
                        "promoting immediate workplace transference.",

                "The PMP signifies that you speak and understand the\n" +
                        " global language of project management and connects \n" +
                        "you to a community of professionals, organizations \n" +
                        "and experts worldwide. Become a PMP and become a \n" +
                        "project hero. This Course Provides everything you \n" +
                        "need to know to pass the PMP Exam and become a \n" +
                        "Certified Project Management professional.",

                "Process for employers to participate in the \n" +
                        "New Atlantic initiative programs and pathways \n" +
                        "to immigration.Increasing Cultural Diversity.\n" +
                        "* Building the NS Economy.\n" +
                        "* Increasing opportunity.\n" +
                        "* Boosts Innovation\n" +
                        "FOR INFORMATION Contact US NOW\n" +
                        "(902) 880-2428\n" +
                        "info@ibtradeshow.com\n" +
                        "WWW.IBTRADESHOW.COM",

                "Air Traffic Control is coming back to play \n" +
                        "the Grand Banker in Lunenburg Nova Scotia. \n" +
                        "Air Traffic Control is a rock band with an \n" +
                        "alternative twist. The independent and \n" +
                        "self-managed three-piece originated as \n" +
                        "Madhat and earned three nominations for \n" +
                        "Alternative Recording of the Year by the \n" +
                        "East Coast Music Association.",

                "HOPSIN is finally coming to HALIFAX! \n" +
                        "With Millions of followers and hundreds\n" +
                        " of millions of views on his YouTube videos,\n" +
                        " this guy is a Hip Hop legend who's only \n" +
                        "just getting started! This Canadian Tour \n" +
                        "will also feature TOKEN, who is being \n" +
                        "heralded as the next Eminem!",

                "Always wanted to experience the magic and \n" +
                        "music of Broadway, but couldn't get to New \n" +
                        "York? Dartmouth Choral Society has you \n" +
                        "covered! In its annual spring concert, DCS \n" +
                        "brings some of Broadway's best-known and \n" +
                        "best-loved hits to cool, choral life - right \n" +
                        "in your own backyard! For just $15, you \n" +
                        "can experience classics from Les Miserables, \n" +
                        "Cats, Rent, West Side Story, The Phantom of \n" +
                        "the Opera, and more!",

                "Come Join us for a fun night @ LWF Hall. We\n" +
                        "will be hosting a Paint the Town Fundraiser \n" +
                        "in support of KELOOSE. 2017 is our 40th Year\n" +
                        "and we want to make it a memorable year! ",

                "A Syrian cultural night put on by Syrian \n" +
                        "newcomers to give back to the community.\n" +
                        "Experience authentic Syrian culture and \n" +
                        "traditions in this unique show with a \n" +
                        "classic live action play, a singing \n" +
                        "performance, delicious Syrian food, and \n" +
                        "an exciting dance of swords and shields. \n" +
                        "Written and performed by Syrian newcomers \n" +
                        "and students, this event will provide you \n" +
                        "with the best and most authentic experience \n" +
                        "of Syrian culture!",

                "The Artists of Dalhousie society is excited\n" +
                        "to launch their Inaugural Art Exhibition \n" +
                        "happening on Monday, April 3rd at 2:30 pm \n" +
                        "in the SUB. That's right, Dalhousie is \n" +
                        "getting its' own student and Dal/King's \n" +
                        "community Art Gallery on the walls of the \n" +
                        "SUB'S atrium and more! We are thrilled to \n" +
                        "be bringing more visual arts into the SUB!\n" +
                        "And that's where you incredible humans \n" +
                        "come in! We're putting out a call for \n" +
                        "submissions for students, alumni, faculty \n" +
                        "can submit artwork of their creation to us!",

                "Kamphyre studio is delighted to welcome \n" +
                        "the producers, directors and actors of \n" +
                        "an exciting new musical production, KAMP,\n" +
                        "as hosts for the April edition of our \n" +
                        "Kamphyre Cabaret. colliding worlds and \n" +
                        "new faces within the Halifax arts \n" +
                        "community is sure to uplift and inspire \n" +
                        "anyone partaking or as guests at this \n" +
                        "event. This cabaret is a fundraiser we \n" +
                        "are thrilled to host which will support \n" +
                        "the financial undertaking of an artistic \n" +
                        "endeavour of this calibre.",

                "Come Join the fun as we gather once more \n" +
                        "for the MOST AFFORDABLE weekend of geeky \n" +
                        "goodness! Support your favorite clubs \n" +
                        "gathered here to fundraise for the year \n" +
                        "ahead! This years cohost clubs are JVPS \n" +
                        ",SCA, NERO ARMONIA,IKG,MHA,FRAG and of \n" +
                        "course Geeks Versus Nerds - back with \n" +
                        "mayhem and VIP party goodness. ",

                "In the struggle to meet the challenges of \n" +
                        "everyday living, the richness, beauty and \n" +
                        "sacredness of life often remain hidden in \n" +
                        "the shadows of the mundane.This retreat \n" +
                        "offers an opportunity to explore beneath \n" +
                        "the layers of ordinariness and enter the \n" +
                        "inner chambers of your being. Strengthen \n" +
                        "your foundation of self-respect, inner \n" +
                        "power and love that is the glue for your \n" +
                        "life. Discover the humility to offer your \n" +
                        "unique gift to the world. Enjoy a day of \n" +
                        "simple exercises, reflective time and \n" +
                        "guided meditations. Designed for the \n" +
                        "modern multi-tasking woman with an \n" +
                        "interest in practical spirituality.",

                "This two and a half hour intensive review \n" +
                        "session with an IELTS specialist will help \n" +
                        "you prepare for your upcoming IELTS test. \n" +
                        "Learn strategies to help you with your \n" +
                        "listening, reading and writing. Know what \n" +
                        "to expect when you take your test, and \n" +
                        "feel more confident. Ask questions and \n" +
                        "get feedback on all four parts of the test."
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
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL,
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.MARCH,
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL
        };

        // todo set (1-31)
        int[] posterStartDateDays = {
                20, 28, 28, 10, 9,
                15, 30, 8, 29, 30,
                3, 7, 22, 22, 26
        };

        // todo set (1-24)
        int[] posterStartDateHours = {
                14, 18, 9, 9, 14,
                22, 20, 19, 19, 18,
                14, 20, 11, 10, 17
        };

        // todo set (0-59)
        int[] posterStartDateMinutes = {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 30, 0,
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
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL,
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL, Calendar.APRIL,
                Calendar.APRIL, Calendar.MARCH, Calendar.APRIL, Calendar.APRIL,
                Calendar.APRIL, Calendar.APRIL, Calendar.APRIL
        };

        // todo set (1-31)
        int[] posterEndDateDays = {
                20, 28, 28, 13, 9,
                16, 30, 8, 29, 30,
                3, 8, 23, 22, 27
        };

        // todo set (1-24)
        int[] posterEndDateHours = {
                17, 23, 17, 17, 23,
                23, 23, 22, 21, 21,
                16, 23, 18, 16, 19
        };

        // todo set (0-59)
        int[] posterEndDateMinutes = {
                0, 0, 0, 0, 0,
                59, 0, 0, 30, 0,
                0, 59, 0, 0, 30
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

            poster.setPhotoName("poster_" + (i + 1) + ".png");

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
