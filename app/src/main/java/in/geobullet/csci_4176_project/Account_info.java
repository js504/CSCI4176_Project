/*
*    earth image source: https://icons8.com/web-app/5561/globe
*    pin image http://fabricmate.com/tackable-panels
*
*/

package in.geobullet.csci_4176_project;

import android.content.Context;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.geobullet.csci_4176_project.Shared.SessionData;
import in.geobullet.csci_4176_project.Utils.NavMenuManager;
import in.geobullet.csci_4176_project.Utils.NavViewListener;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.User;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;

public class Account_info extends AppCompatActivity {

    //hardcoded poster items
    private NavigationView navigationView;
    private NavMenuManager navManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        //hide keyboard when start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //db user info
        final DatabaseHandler db = new DatabaseHandler(this);

        int id = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("user");
            Log.i("id",Integer.toString(id));
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavViewListener(this));
        navManager = new NavMenuManager();

        final TextView username = (TextView) findViewById(R.id.editUsername);
        final TextView first_name = (TextView) findViewById(R.id.edit_firstname);
        final TextView last_name = (TextView) findViewById(R.id.editLastname);
        final TextView email = (TextView) findViewById(R.id.edit_Email);
        final TextView oldpwd = (TextView) findViewById(R.id.editOldpwd);
        final TextView newpwd = (TextView) findViewById(R.id.edit_Newpwd);

        final User currentUser = SessionData.currentUser;
        List<Poster> userPoster = null;
        if (currentUser != null) {
            username.setText(currentUser.getDisplayName());
            first_name.setText(currentUser.getFirstName());
            last_name.setText(currentUser.getLastName());
            email.setText(currentUser.getEmail());
        }


        //button to update the user's information
        Button submit_button = (Button) findViewById(R.id.submit_changes);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                assert currentUser != null;
                if(oldpwd.getText().toString().equals(currentUser.getPassword())) {
                    currentUser.setDisplayName(username.getText().toString());
                    currentUser.setFirstName(first_name.getText().toString());
                    currentUser.setLastName(last_name.getText().toString());
                    currentUser.setEmail(email.getText().toString());
                    currentUser.setPassword(newpwd.getText().toString());

                    db.updateUser(currentUser);
                    Intent intent = new Intent(Account_info.this, Account_info.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    //showing toast message alert user that the original password not match when trying to change user information
                    Context context = getApplicationContext();
                    CharSequence text = "Original password not valid!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


        //menu component
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //display login username at toolbar
        getSupportActionBar().setTitle("Login as: " + (currentUser == null ? "" : currentUser.getDisplayName()));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Changed how nav view operates, listener has now been moved into its own class so repeat code is avoided
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavViewListener(this));

        //update header information about user
        View hView =  navigationView.getHeaderView(0);
        TextView welcome_menu = (TextView)hView.findViewById(R.id.nav_welcome);
        welcome_menu.setText("Welcome: " + SessionData.currentUser.getFirstName() +" "+ SessionData.currentUser.getLastName());


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_right_top_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SessionData.currentUser = null;
            Intent intent = new Intent(Account_info.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
