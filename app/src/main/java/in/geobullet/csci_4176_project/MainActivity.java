package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.PosterType;
import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /* Begin Database Seeding */

        Log.d("DBhandler", "Creating dbHandler..");
        final DatabaseHandler dbHandler = new DatabaseHandler(this);
        Log.d("DBhandler", "dbHandler created.");

        Log.d("Seeding", "************************************ Begin Seeding Database *******************************************");

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


        Log.d("Seeding", "************************************ End Seeding Database *******************************************");

        /* End Database Seeding */




        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Well, hello there!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_something1) {
            return true;
        } else if (id == R.id.action_something2) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //swtich the layout of the content
        //ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accountInfo) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);

        } else if (id == R.id.nav_MainGUI) {

            Intent i = new Intent(MainActivity.this, Main_GUI.class);
            startActivity(i);
            //vf.setDisplayedChild(2);

        } else if (id == R.id.nav_mapGUI) {

            //Intent i = new Intent(MainActivity.this, MapsActivity.class);
            //startService(i);
            //vf.setDisplayedChild(1);

        } else if (id == R.id.create_poster) {

            Intent i = new Intent(MainActivity.this, CreateNewPoster.class);
            startActivity(i);
            //vf.setDisplayedChild(2);

        } else if (id == R.id.nav_manageBulletins) {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        } else if (id == R.id.create_nearByBulletins) {

        } else if (id == R.id.nav_searchEvents) {

        } else if (id == R.id.nav_manageEvents) {

        } else if (id == R.id.nav_createEvents) {

        } else if (id == R.id.nav_addEvent) {

        } else if (id == R.id.nav_delBulletinBoards) {

        } else if (id == R.id.nav_achievement) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
