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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.PosterType;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Utils.DateUtil;
import in.geobullet.csci_4176_project.Shared.SessionData;
import in.geobullet.csci_4176_project.Utils.NavMenuManager;
import in.geobullet.csci_4176_project.Utils.NavViewListener;


public class Main_GUI extends AppCompatActivity implements View.OnClickListener{

    private NavMenuManager navManager;
    private NavigationView navigationView;

    final DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui);

        String selected_poster_type = null;

        navManager = new NavMenuManager();
        //Changed how nav view operates, listener has now been moved into its own class so repeat code is avoided
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavViewListener(this));

        Intent intent = this.getIntent();
        Board board = null;

        if(intent != null) {
            selected_poster_type = intent.getStringExtra("postertype");
            int id;
            // If there is an int passed, then get the board associated with it.
            if((id = intent.getIntExtra(MapsActivity.MAPS_BOARD_ID_KEY, -1)) != -1){
                board = dbHandler.getBoardById(id);
            }
        }
        if(board == null){
            board = dbHandler.getFirstBoard();
        }

        List<Poster> postersForBoard1 = dbHandler.getPostersForBoard(board.getId());
        int left_margin_index = 0;
        int top_margin_index = 0;
        int column_index = 0;
        int row_index = 0;
        int poster_count = 0;
        String poster_name = null;

        if (board != null) {

            for (final Poster p : postersForBoard1) {

                if(selected_poster_type == null) // display all posters (Events & Services)
                {
                    ImageButton ib = new ImageButton(this);
                    ib.setOnClickListener(poster_Listener);
                    poster_name = p.getPhotoName();
                    poster_name = poster_name.substring(0, poster_name.lastIndexOf("."));
                    poster_name = poster_name + "_icon";

                    RelativeLayout RelLayout = (RelativeLayout) findViewById(R.id.rel_in_horz);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    int resID = this.getResources().getIdentifier(poster_name, "drawable", this.getPackageName());
                    ib.setImageResource(resID);
                    ib.setId(p.getId());

                    if(poster_count == 0)
                    {
                        params.setMargins(0, 50, 0, 0);
                        poster_count++;
                        params.setMargins(left_margin_index, top_margin_index, 0, 0);
                        ib.setLayoutParams(params);
                        RelLayout.addView(ib);
                        row_index++;
                    }
                    else
                    {
                        if(row_index <=2)
                        {
                            top_margin_index = (600*row_index);
                            left_margin_index = (1500*column_index);
                            params.setMargins(left_margin_index, top_margin_index, 0, 0);
                            ib.setLayoutParams(params);
                            RelLayout.addView(ib);
                            row_index++;
                        }
                        else
                        {
                            column_index++;
                            row_index = 0;
                            left_margin_index = (1500*column_index);
                            top_margin_index = (600*row_index);
                            params.setMargins(left_margin_index, top_margin_index, 0, 0);
                            ib.setLayoutParams(params);
                            RelLayout.addView(ib);
                            row_index++;
                        }
                    }
                }
                else if(selected_poster_type.equals("Event"))
                {
                    if(p.getPosterType().equals(PosterType.Event))
                    {
                        ImageButton ib = new ImageButton(this);
                        ib.setOnClickListener(poster_Listener);
                        poster_name = p.getPhotoName();
                        poster_name = poster_name.substring(0, poster_name.lastIndexOf("."));
                        poster_name = poster_name + "_icon";

                        RelativeLayout RelLayout = (RelativeLayout) findViewById(R.id.rel_in_horz);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        int resID = this.getResources().getIdentifier(poster_name, "drawable", this.getPackageName());
                        ib.setImageResource(resID);
                        ib.setId(p.getId());

                        if(poster_count == 0)
                        {
                            params.setMargins(0, 50, 0, 0);
                            poster_count++;
                            params.setMargins(left_margin_index, top_margin_index, 0, 0);
                            ib.setLayoutParams(params);
                            RelLayout.addView(ib);
                            row_index++;
                        }
                        else
                        {
                            if(row_index <=2)
                            {
                                top_margin_index = (600*row_index);
                                left_margin_index = (1500*column_index);
                                params.setMargins(left_margin_index, top_margin_index, 0, 0);
                                ib.setLayoutParams(params);
                                RelLayout.addView(ib);
                                row_index++;
                            }
                            else
                            {
                                column_index++;
                                row_index = 0;
                                left_margin_index = (1500*column_index);
                                top_margin_index = (600*row_index);
                                params.setMargins(left_margin_index, top_margin_index, 0, 0);
                                ib.setLayoutParams(params);
                                RelLayout.addView(ib);
                                row_index++;
                            }
                        }
                    }
                }
                else        //selected_poster_type == "Service"
                {
                    if(p.getPosterType().equals(PosterType.Service))
                    {
                        ImageButton ib = new ImageButton(this);
                        ib.setOnClickListener(poster_Listener);
                        poster_name = p.getPhotoName();
                        poster_name = poster_name.substring(0, poster_name.lastIndexOf("."));
                        poster_name = poster_name + "_icon";

                        RelativeLayout RelLayout = (RelativeLayout) findViewById(R.id.rel_in_horz);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        int resID = this.getResources().getIdentifier(poster_name, "drawable", this.getPackageName());
                        ib.setImageResource(resID);
                        ib.setId(p.getId());

                        if(poster_count == 0)
                        {
                            params.setMargins(0, 50, 0, 0);
                            poster_count++;
                            params.setMargins(left_margin_index, top_margin_index, 0, 0);
                            ib.setLayoutParams(params);
                            RelLayout.addView(ib);
                            row_index++;
                        }
                        else
                        {
                            if(row_index <=2)
                            {
                                top_margin_index = (600*row_index);
                                left_margin_index = (1500*column_index);
                                params.setMargins(left_margin_index, top_margin_index, 0, 0);
                                ib.setLayoutParams(params);
                                RelLayout.addView(ib);
                                row_index++;
                            }
                            else
                            {
                                column_index++;
                                row_index = 0;
                                left_margin_index = (1500*column_index);
                                top_margin_index = (600*row_index);
                                params.setMargins(left_margin_index, top_margin_index, 0, 0);
                                ib.setLayoutParams(params);
                                RelLayout.addView(ib);
                                row_index++;
                            }
                        }
                    }
                }
            }
        }



        //add menu component
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Future Poster Sharing Functionality", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //Changed how nav view operates, listener has now been moved into its own class so repeat code is avoided
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavViewListener(this));


        View hView =  navigationView.getHeaderView(0);
        TextView welcome_menu = (TextView)hView.findViewById(R.id.nav_welcome);
        if(SessionData.currentUser != null) {
            welcome_menu.setText("Welcome: " + SessionData.currentUser.getFirstName() + " " + SessionData.currentUser.getLastName());
        }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if a user has logged in , if so show the hidden menu items
        if (navigationView != null) {
            if (SessionData.currentUser != null) {
                navManager.showUserMenuItems(navigationView);
            } else {
                navManager.hideUserMenuItem(navigationView);
            }
        }
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
        getMenuInflater().inflate(R.menu.main_gui_right_top_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        // default method for handling onClick Events..
    }

    private View.OnClickListener poster_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Board board = dbHandler.getFirstBoard();
            List<Poster> postersForBoard1 = dbHandler.getPostersForBoard(board.getId());
            String poster_details = "Empty";
            String poster_name_pass = "Empty";
            String poster_address = "Empty";
            String poster_sdate = "Empty";
            String poster_edate = "Empty";
            String poster_cost = "Empty";

            int ib_ident = v.getId();

            for (final Poster p : postersForBoard1) {
                if((p.getId())== ib_ident)
                {
                    poster_details = p.getDetails();
                    poster_address = p.getAddress();
                    poster_sdate = DateUtil.formatDate(p.getStartDate());
                    poster_edate = DateUtil.formatDate(p.getEndDate());
                    poster_cost = p.getCost();
                    poster_name_pass = p.getPhotoName();
                    poster_name_pass = poster_name_pass.substring(0, poster_name_pass.lastIndexOf("."));

                    Intent intent = new Intent(Main_GUI.this, Poster_Look.class);
                    intent.putExtra("postername", poster_name_pass);
                    intent.putExtra("posterdetails", poster_details);
                    intent.putExtra("posteraddress", poster_address);
                    intent.putExtra("postersdate", poster_sdate);
                    intent.putExtra("posteredate", poster_edate);
                    intent.putExtra("postercost", poster_cost);

                    startActivity(intent);
                }
            }
        }
    };
}
