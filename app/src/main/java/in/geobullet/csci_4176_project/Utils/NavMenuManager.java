package in.geobullet.csci_4176_project.Utils;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Shared.SessionData;

/**
 * Created by Nick on 2017-04-02.
 */

public class NavMenuManager {

    /**
     * Function hides user menu items when a user has logged in based on the SessionData class
     *
     * @param navigationView  The navigation view to set the menu items visible for
     */
    public void hideUserMenuItem(NavigationView navigationView) {

        if (SessionData.currentUser == null) {

            if (navigationView != null) {

                Menu navMenu = navigationView.getMenu();

                if (navMenu != null) {

                    MenuItem menuItem = navMenu.findItem(R.id.admin_tools_menu);

                    if (menuItem != null) {
                        menuItem.setVisible(false);
                    }

                    menuItem = navMenu.findItem(R.id.manage_my_posters);

                    if (menuItem != null) {
                        menuItem.setVisible(false);
                    }

                    menuItem = navMenu.findItem(R.id.nav_accountInfo);

                    if (menuItem != null) {
                        menuItem.setTitle("Log in");
                    }
                }
            }
        }
    }

    /**
     * Function shows user menu items when a user has logged in based on the SessionData class
     *
     * @param navigationView  The navigation view to set the menu items visible for
     */
    public void showUserMenuItems(NavigationView navigationView) {

        if (SessionData.currentUser != null) {

            if (navigationView != null) {

                Menu navMenu = navigationView.getMenu();

                if (navMenu != null) {

                    MenuItem menuItem = navMenu.findItem(R.id.manage_my_posters);
                    if (menuItem != null) {
                        menuItem.setVisible(true);
                    }

                    if (SessionData.currentUser.isAdmin()) {
                        menuItem = navMenu.findItem(R.id.admin_tools_menu);
                        if (menuItem != null) {
                            menuItem.setVisible(true);
                        }
                    }
                }
            }
        }
    }

}
