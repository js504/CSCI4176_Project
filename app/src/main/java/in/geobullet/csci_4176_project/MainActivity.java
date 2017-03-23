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

        User u1 = new User();
        u1.setFirstName("Johnny");
        u1.setLastName("B. Goode.");
        u1.setDisplayName("JBG");
        u1.setEmail("JBG@goody.com");
        u1.setPassword("YouGuessedIt");
        u1.setAdmin(true);

        int user1Id = (int) dbHandler.addUser(u1);

        u1.setId(user1Id);

        Log.i("UserId:", Integer.toString(u1.getId()));

        User currentUser = dbHandler.getUserById(user1Id);

        Log.d("CurrentUser", currentUser.toString());

//        Board b1 = new Board();
//        b1.setCreated(new Date(2017, 3, 23));
//        b1.setCreatedByUserId(currentUser.getId());
//        b1.setName("Local Bulletin 1");
//        b1.setExpirationDate(null);
//        b1.setRadiusInMeters(1000);
//        b1.setLongitude(63.5917);
//        b1.setLatitude(44.6366);
//
//        Log.d("Seeding", "Adding board..");
//        int board1Id = (int) dbHandler.addBoard(b1);
//        Log.d("Seeding", "Added board. Id: " + board1Id);
//
//        b1 = dbHandler.getBoardById(board1Id);
//
//        if (b1 != null) {
//            Log.d("Seeding", "Queried board by id:");
//            Log.d("Board that was added:", b1.toString());
//        }

//        Poster p1 = new Poster();
//        p1.setCreated(new Date());
//        p1.setCreatedByUserId(currentUser.getId());
//        p1.setTitle("A Tribute To Johnny Cash");
//        p1.setPosterType(PosterType.Event);
//        p1.setAddress("Strathspey Place");
//        p1.setCity("Mabou");
//        p1.setStateProv("NS");
//        p1.setDetails("Who doesn't like Johnny Cash?");
////        Calendar sDate = new GregorianCalendar(2017, Calendar.JUNE, 20);
//        p1.setStartDate(null);
//        p1.setEndDate(null);
//        p1.setStartTime(null);
//        p1.setEndTime(null);
//        p1.setPhotoName("poster_1.jpg");
//
//        int poster1Id = (int) dbHandler.addPoster(p1);
//
//        Log.d("Seeding", "Added poster. Id: " + poster1Id);


        // todo add the rest of the posters

//        BoardPosterPair bpp1 = new BoardPosterPair();
//        bpp1.setBoardId(board1Id);
//        bpp1.setPosterId(poster1Id);
//        int bpp1Id = (int) dbHandler.addBoardPosterPair(bpp1);
//
//        Log.d("Seeding", "Added board poster pair: Id: " + bpp1Id);


        // todo finish seeding

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
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(i);
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
